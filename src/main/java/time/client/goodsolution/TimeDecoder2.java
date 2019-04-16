package time.client.goodsolution;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class TimeDecoder2 extends ByteToMessageDecoder {
//  ByteToMessageDecoder is an implementation of ChannelInboundHandler
//  which makes it easy to deal with the fragmentation issue

  @Override
  protected void decode(ChannelHandlerContext context, ByteBuf inByteBuf, List<Object> outList)
      throws Exception {
//    ByteToMessageDecoder calls the decode() method
//    with an internally maintained cumulative buffer whenever new data is received.
    if (inByteBuf.readableBytes() < 4) {
      return;
//      decode() can decide to add nothing to out where there is not enough data in the cumulative buffer.
//      ByteToMessageDecoder will call decode() again when there is more data received.
    }

    outList.add(inByteBuf.readBytes(4));
//    If decode() adds an object to out,
//    it means the decoder decoded a message successfully.
//    ByteToMessageDecoder will discard the read part of the cumulative buffer.
//    Please remember that you don't need to decode multiple messages.
//    ByteToMessageDecoder will keep calling the decode() method until it adds nothing to out.
  }

//  Additionally, Netty provides out-of-the-box decoders
//  which enables you to implement most protocols very easily
//  and helps you avoid from ending up
//  with a monolithic unmaintainable handler implementation.
//  Please refer to the following packages for more detailed examples:
//  io.netty.example.factorial for a binary protocol, and
//  io.netty.example.telnet for a text line-based protocol.

}
