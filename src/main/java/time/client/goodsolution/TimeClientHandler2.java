package time.client.goodsolution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler2 extends ChannelInboundHandlerAdapter {

  private ByteBuf buf;

  @Override
  public void handlerAdded(ChannelHandlerContext context) {
    buf = context.alloc().buffer(4);
//    A ChannelHandler has two life cycle listener methods:
//    handlerAdded() and handlerRemoved().
//    You can perform an arbitrary (de)initialization task as long as it does not block for a long time.
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext context) {
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

    //second solution - new handler - TimeDecoder2 in pipeline
    // and second realisation without check bytes count (it is in decoder)
    long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
    System.out.println(new Date(currentTimeMillis));
    context.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }
}
