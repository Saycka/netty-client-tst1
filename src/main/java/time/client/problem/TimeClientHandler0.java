package time.client.problem;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler0 extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    ByteBuf msg = (ByteBuf) message;
//    In TCP/IP, Netty reads the data sent from a peer into a ByteBuf.

    //problem
    //sometimes will refuse to work raising an IndexOutOfBoundsException
    //Because the buffer of a stream-based transport is not a queue of packets but a queue of bytes
    try {
      long currentTimeMillis = (msg.readUnsignedInt() - 2208988800L) * 1000L;
      System.out.println(new Date(currentTimeMillis));
      context.close();
    } finally {
      msg.release();
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }
}
