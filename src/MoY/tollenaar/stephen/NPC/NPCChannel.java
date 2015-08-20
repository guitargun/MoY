package MoY.tollenaar.stephen.NPC;

import java.net.SocketAddress;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;

public class NPCChannel extends AbstractChannel{
	 private final ChannelConfig config = new DefaultChannelConfig(this);
	
	 protected NPCChannel(Channel parent) {
		super(parent);
	}

	    public ChannelConfig config() {
	        config.setAutoRead(true);
	        return config;
	    }

	    public boolean isActive() {
	        return false;
	    }

	    public boolean isOpen() {
	        return false;
	    }

	    public ChannelMetadata metadata() {
	        return null;
	    }

	    @Override
	    protected void doBeginRead() throws Exception {
	    }

	    @Override
	    protected void doBind(SocketAddress arg0) throws Exception {
	    }

	    @Override
	    protected void doClose() throws Exception {
	    }

	    @Override
	    protected void doDisconnect() throws Exception {
	    }

	    @Override
	    protected void doWrite(ChannelOutboundBuffer arg0) throws Exception {
	    }

	    @Override
	    protected boolean isCompatible(EventLoop arg0) {
	        return true;
	    }

	    @Override
	    protected SocketAddress localAddress0() {
	        return null;
	    }

	    @Override
	    protected AbstractUnsafe newUnsafe() {
	        return null;
	    }

	    @Override
	    protected SocketAddress remoteAddress0() {
	        return null;
	    }

}
