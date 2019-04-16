package time.client.badsolution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler1 extends ChannelInboundHandlerAdapter {

  private ByteBuf buf;

  @Override
  public void handlerAdded(ChannelHandlerContext context) {  //diff
    buf = context.alloc().buffer(4);
//    A ChannelHandler has two life cycle listener methods:
//    handlerAdded() and handlerRemoved().
//    You can perform an arbitrary (de)initialization task as long as it does not block for a long time.
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext context) { //diff
    buf.release();
    buf = null;
  }

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    ByteBuf msg = (ByteBuf) message;
//    In TCP/IP, Netty reads the data sent from a peer into a ByteBuf.

    buf.writeBytes(msg);
//    First, all received data should be cumulated into buf.
    msg.release();

    //first solution - do smt after read all bytes
    //but what if data have variable length field
    if (buf.readableBytes() >= 4) {
//      And then, the handler must check if buf has enough data,
//      4 bytes in this example, and proceed to the actual business logic.
//      Otherwise, Netty will call the channelRead() method again
//      when more data arrives, and eventually all 4 bytes will be cumulated.
      long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;     //diff
      System.out.println(new Date(currentTimeMillis));
      context.close();
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }
}
