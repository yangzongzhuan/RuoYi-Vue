package com.ruoyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    private static Logger logger = LoggerFactory.getLogger(RuoYiApplication.class);

    public static void main(String[] args) throws UnknownHostException
    {
        ConfigurableApplicationContext application = SpringApplication.run(RuoYiApplication.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String active = env.getProperty("spring.profiles.active");
        String port = env.getProperty("server.port");
        port = port == null ? "8080" : port;
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        String xxlJobAddresses = env.getProperty("xxl.job.admin.addresses");
        xxlJobAddresses = xxlJobAddresses == null ? "" : xxlJobAddresses;
        logger.info("\n----------------------------------------------------------\n\t" +
                "系统运行环境 : \t" +active + "\n\t" +
                "本地访问地址 : \thttp://localhost:" + port + path + "\n\t" +
                "外部访问地址 : \thttp://" + ip + ":" + port + path + "\n\t" +
                "在线文档地址 : \thttp://" + ip + ":" + port + path + "swagger-ui/index.html\n\t" +
                "----------------------------------------------------------");

//        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
//                " .-------.       ____     __        \n" +
//                " |  _ _   \\      \\   \\   /  /    \n" +
//                " | ( ' )  |       \\  _. /  '       \n" +
//                " |(_ o _) /        _( )_ .'         \n" +
//                " | (_,_).' __  ___(_ o _)'          \n" +
//                " |  |\\ \\  |  ||   |(_,_)'         \n" +
//                " |  | \\ `'   /|   `-'  /           \n" +
//                " |  |  \\    /  \\      /           \n" +
//                " ''-'   `'-'    `-..-'              ");
    }
}
