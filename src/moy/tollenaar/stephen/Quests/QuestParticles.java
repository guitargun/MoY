package moy.tollenaar.stephen.Quests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import moy.tollenaar.stephen.MistsOfYsir.MoY;
import moy.tollenaar.stephen.PlayerInfo.Playerstats;
import moy.tollenaar.stephen.Util.ParticleEffect;

public class QuestParticles {
	private static MoY plugin;
	private static QuestsServerSide q;

	public QuestParticles(MoY instance) {
		QuestParticles.plugin = instance;
		QuestParticles.q = instance.qserver;
	}

	public static void collectplayers(Location loc, UUID npcuuid) {
		double radiussq = Math.pow(40, 2);
		ArrayList<Player> allclosep = new ArrayList<Player>();
		if (Bukkit.getOnlinePlayers().size() >= 1) {
			for(Player player : Bukkit.getOnlinePlayers()){
				if (player.getWorld().getName()
						.equals(loc.getWorld().getName())) {
					if (player.getLocation().distanceSquared(loc) <= radiussq) {

						allclosep.add(player);

					}
				}
			}
		}
		if (allclosep.size() > 0) {
			sendnormalques(npcuuid, allclosep, loc);
		}
	}

	private static void sendnormalques(UUID npcuuid, ArrayList<Player> players,
			Location loc) {
		for (Player player : players) {
			ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
			if (aviableq(npcuuid, player)) {
				effects.add(ParticleEffect.NOTE);
			}
			if (aviablew(npcuuid)) {
				effects.add(ParticleEffect.VILLAGER_HAPPY);
			}
			if (completedq(npcuuid, player)) {
				effects.add(ParticleEffect.SPELL);
			}
			if (tocompletetalk(npcuuid, player)) {
				effects.add(ParticleEffect.HEART);
			}
			ArrayList<Player> t = new ArrayList<Player>();
			t.add(player);
			loc.setY(loc.getY() + 2);
			for (ParticleEffect effect : effects) {

				effect.display(0, 0, 0, 0, 1, loc, t);
			}
			loc.setY(loc.getY() - 2);

		}
	}

	private static boolean tocompletetalk(UUID npcuuid, Player player) {
		Playerstats p = q.playerinfo.getplayer(player.getUniqueId());
		if (p.getactivetype() != null) {
			if (p.getactives("talkto") != null) {
				for (Integer i : p.getactives("talkto")) {
					QuestTalkto talk = q.returntalkto(i);
					if(talk == null){
						p.deleteactive("talkto", i);
						continue;
					}
					if (plugin.qserver.uniquenpcid.get(talk.getNpcid()) == npcuuid) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean completedq(UUID npcuuid, Player player) {
		boolean checks = false;
		Playerstats p = q.playerinfo.getplayer(player.getUniqueId());
		if (p.getcompletedtype() != null) {
			for (String type : p.getcompletedtype()) {
				for (int number : p.getcompleted(type)) {
					try {
						if (plugin.qserver.GetIds(type, npcuuid).contains(
								number)) {
							return true;
						}
					} catch (NullPointerException ex) {
						continue;
					}
				}
			}
		}
		return checks;
	}

	private static boolean aviablew(UUID npcuuid) {
		if (plugin.qserver.getWarpId(npcuuid) != -1) {
			Warps warp = plugin.qserver.returnwarp(plugin.qserver.getWarpId(npcuuid));
			if(warp == null){
				plugin.qserver.removewarp(plugin.qserver.getWarpId(npcuuid));
				return false;
			}
			if(warp.getState().equals("active")){
				for(String type : warp.getType()){
					for(Warps w : Warps.TypeReturned(type)){
						if(w.getState().equals("active") && warp.getWarpid() != w.getWarpid()){
							if (warp.getByPassID().size() != 0) {
								if (warp.isOverrideOnly()) {
									if (!plugin.tr
											.containsID(warp.getByPassID(),
													w.getByPassID())) {
										continue;
									}else{
										return true;
									}
								}
							}else{
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean aviableq(UUID npcuuid, Player player) {
		if (plugin.qserver.GetIds("kill", npcuuid) != null) {
			for (Integer in : plugin.qserver.GetIds("kill", npcuuid)) {
				if (check(npcuuid, player, in, "kill")) {
					return true;
				}
			}
		}

		if (plugin.qserver.GetIds("harvest", npcuuid) != null) {
			for (Integer in : plugin.qserver.GetIds("harvest", npcuuid)) {
				if (check(npcuuid, player, in, "harvest")) {
					return true;
				}
			}
		}

		if (plugin.qserver.GetIds("talkto", npcuuid) != null) {
			for (Integer in : plugin.qserver.GetIds("talkto", npcuuid)) {
				if (check(npcuuid, player, in, "talkto")) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean check(UUID npcuuid, Player player, int questnumber,
			String type) {
		Playerstats p = q.playerinfo.getplayer(player.getUniqueId());
		switch (type) {
		case "kill":
			QuestKill kill = q.returnkill(questnumber);

			if (kill != null) {
				if (kill.getState().equals("active")) {
					if (!kill.getPrereq().split("=")[0].equals("none")) {
						String[] preq = kill.getPrereq().split("=");

						if (p.getrewardedtype() != null) {
							if (p.getrewardednumber(preq[0].trim()) != null) {
								if (!p.getrewardednumber(preq[0].trim())
										.contains(
												Integer.parseInt(preq[1].trim()))) {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					}

					if (p.getactivetype() != null) {
						if (p.getactives(type) != null) {
							if (p.getactives(type).contains(questnumber)) {
								return false;
							}
						}
					}
					if (p.getcompletedtype() != null) {
						if (p.getcompleted(type) != null) {
							if (p.getcompleted(type).contains(questnumber)) {
								return false;
							}
						}
					}
					if (p.getrewardedtype() != null) {
						if (p.getrewardednumber(type) != null) {
							if (p.getrewardedtime(type, questnumber) != 0) {
								if (!q.returnkill(questnumber).getDelay()
										.equals("-1")) {
									long logged = parseDateDiff(
											q.returnkill(questnumber)
													.getDelay(),
											true,
											p.getrewardedtime(type, questnumber));
									if (logged <= System.currentTimeMillis()) {

										p.deleterewarded(type, questnumber);
										q.playerinfo.saveplayerdata(p);
									} else {
										return false;
									}
								} else {
									return false;
								}
							}
						}
					}
				}else{
					return false;
				}
			} else {
				q.removekill(questnumber);
				return false;
				
			}
			break;
		case "harvest":
			QuestHarvest harvest = q.returnharvest(questnumber);

			if (harvest != null) {
				if (harvest.getState().equals("active")) {
					if (!harvest.getPrereq().split("=")[0].equals("none")) {
						String[] preq = harvest.getPrereq().split("=");

						if (p.getrewardedtype() != null) {
							if (p.getrewardednumber(preq[0].trim()) != null) {
								if (!p.getrewardednumber(preq[0].trim())
										.contains(
												Integer.parseInt(preq[1].trim()))) {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					}
					if (p.getactivetype() != null) {
						if (p.getactives(type) != null) {
							if (p.getactives(type).contains(questnumber)) {
								return false;
							}
						}
					}
					if (p.getcompletedtype() != null) {
						if (p.getcompleted(type) != null) {
							if (p.getcompleted(type).contains(questnumber)) {
								return false;
							}
						}
					}
					if (p.getrewardedtype() != null) {
						if (p.getrewardednumber(type) != null) {
							if (p.getrewardedtime(type, questnumber) != 0) {
								if (!q.returnharvest(questnumber).getDelay()
										.equals("-1")) {
									long logged = parseDateDiff(
											q.returnharvest(questnumber)
													.getDelay(),
											true,
											p.getrewardedtime(type, questnumber));
									if (logged <= System.currentTimeMillis()) {
										p.deleterewarded(type, questnumber);
										q.playerinfo.saveplayerdata(p);
									} else {
										return false;
									}
								} else {
									return false;
								}
							}
						}
					}
				}else{
					return false;
				}
			} else {
				q.removeharvest(questnumber);
				return false;
			
			}
			break;
		case "talkto":
			QuestTalkto talk = q.returntalkto(questnumber);
			if (talk != null) {
				if (talk.getState().equals("active")) {
					if (!talk.getPrereq().split("=")[0].equals("none")) {
						String[] preq = talk.getPrereq().split("=");

						if (p.getrewardedtype() != null) {
							if (p.getrewardednumber(preq[0].trim()) != null) {
								if (!p.getrewardednumber(preq[0].trim())
										.contains(
												Integer.parseInt(preq[1].trim()))) {
									return false;
								}
							} else {
								return false;
							}
						} else {
							return false;
						}
					}
					if (p.getactivetype() != null) {
						if (p.getactives(type) != null) {
							if (p.getactives(type).contains(questnumber)) {
								return false;
							}
						}
					}
					if (p.getcompletedtype() != null) {
						if (p.getcompleted(type) != null) {
							if (p.getcompleted(type).contains(questnumber)) {
								return false;
							}
						}
					}
					if (p.getrewardedtype() != null) {
						if (p.getrewardednumber(type) != null) {
							if (p.getrewardedtime(type, questnumber) != 0) {
								if (!q.returntalkto(questnumber).getDelay()
										.equals("-1")) {
									long logged = parseDateDiff(
											q.returntalkto(questnumber)
													.getDelay(),
											true,
											p.getrewardedtime(type, questnumber));
									if (logged <= System.currentTimeMillis()) {
										p.deleterewarded(type, questnumber);
										q.playerinfo.saveplayerdata(p);
									} else {
										return false;
									}
								} else {
									return false;
								}
							}
						}
					}
					break;
				}else{
					return false;
				}
			} else {
				q.removetalkto(questnumber);
				return false;
				
			}
		}

		return true;
	}

	private static long parseDateDiff(String time, boolean future,
			long rewardedtime) {
		Pattern timePattern = Pattern
				.compile(
						"(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?",

						2);
		Matcher m = timePattern.matcher(time);
		int years = 0;
		int months = 0;
		int weeks = 0;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		boolean found = false;
		while (m.find()) {
			if ((m.group() != null) && (!m.group().isEmpty())) {
				for (int i = 0; i < m.groupCount(); i++) {
					if ((m.group(i) != null) && (!m.group(i).isEmpty())) {
						found = true;
						break;
					}
				}
				if (found) {
					if ((m.group(1) != null) && (!m.group(1).isEmpty())) {
						years = Integer.parseInt(m.group(1));
					}
					if ((m.group(2) != null) && (!m.group(2).isEmpty())) {
						months = Integer.parseInt(m.group(2));
					}
					if ((m.group(3) != null) && (!m.group(3).isEmpty())) {
						weeks = Integer.parseInt(m.group(3));
					}
					if ((m.group(4) != null) && (!m.group(4).isEmpty())) {
						days = Integer.parseInt(m.group(4));
					}
					if ((m.group(5) != null) && (!m.group(5).isEmpty())) {
						hours = Integer.parseInt(m.group(5));
					}
					if ((m.group(6) != null) && (!m.group(6).isEmpty())) {
						minutes = Integer.parseInt(m.group(6));
					}
					if ((m.group(7) == null) || (m.group(7).isEmpty())) {
						break;
					}
					seconds = Integer.parseInt(m.group(7));

					break;
				}
			}
		}
		if (!found) {
			return -1L;
		}
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(rewardedtime);
		if (years > 0) {
			c.add(1, years * (future ? 1 : -1));
		}
		if (months > 0) {
			c.add(2, months * (future ? 1 : -1));
		}
		if (weeks > 0) {
			c.add(3, weeks * (future ? 1 : -1));
		}
		if (days > 0) {
			c.add(5, days * (future ? 1 : -1));
		}
		if (hours > 0) {
			c.add(11, hours * (future ? 1 : -1));
		}
		if (minutes > 0) {
			c.add(12, minutes * (future ? 1 : -1));
		}
		if (seconds > 0) {
			c.add(13, seconds * (future ? 1 : -1));
		}

		return c.getTimeInMillis();
	}

}
