package time.client.pojosolution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler3 extends ChannelInboundHandlerAdapter {


  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    UnixTime m = (UnixTime) message;
    System.out.println(m);
    context.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
    cause.printStackTrace();
    context.close();
  }
}
