package time.client.badsolution;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient1 {

  public static void main(String[] args) throws Exception {

    String host = "localhost";
    int port = 8080;
    if (args.length > 1) {
      host = args[0];
      port = Integer.parseInt(args[1]);
    }

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
//      Bootstrap is similar to ServerBootstrap except that it's for non-server channels
//      such as a client-side or connectionless channel.

      bootstrap.group(workerGroup);
//      If you specify only one EventLoopGroup,
//      it will be used both as a boss group and as a worker group.
//      The boss worker is not used for the client side though.

      bootstrap.channel(NioSocketChannel.class);
//      Instead of NioServerSocketChannel, NioSocketChannel is being used to create a client-side Channel.

      bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
//      Note that we do not use childOption() here unlike we did with ServerBootstrap
//      because the client-side SocketChannel does not have a parent.

      bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          socketChannel.pipeline().addLast(new TimeClientHandler1());
        }
      });

      //start client
      ChannelFuture future = bootstrap.connect(host, port).sync();
//      We should call the connect() method instead of the bind() method.

      //wait until connection is closed
      future.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }

  }

}
