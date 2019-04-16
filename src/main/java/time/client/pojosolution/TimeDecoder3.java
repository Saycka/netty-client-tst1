package time.client.pojosolution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class TimeDecoder3 extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext context, ByteBuf inByteBuf, List<Object> outList)
      throws Exception {
    if (inByteBuf.readableBytes() < 4) {
      return;
    }

    outList.add(new UnixTime(inByteBuf.readUnsignedInt()));
  }
}
