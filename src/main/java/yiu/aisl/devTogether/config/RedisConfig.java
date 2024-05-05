package yiu.aisl.devTogether.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    // redis란? key-value 구조의 비정형 데이터를 저장하고 관리하기 위한 오픈 소스 기반의 비관계형 DBMS
    // Database, Cache, Message Broker 로 사용되며, 인메모리 데이터 구조를 가진 저장소
    // 특징
    // 1. Query를 사용하지 않아도 된다
    // 2. 메모리에서 데이터를 처리하기 때문에 속도가 매우 빠름
    // 3. String, List, Set, Sorted Set, Hash 자료 구조를 지원함
    // 4. Single Threaded임 > 한 번에 하나의 명령만 처리 가능 > 처리 시간이 긴 명령어가 들어오면  그 뒤에 명령어들은 대기가 필요함


    //db가 있는데 redis를 사용해야하는 이유는 뭘까?
    // db는 데이터를 물리디스크에(?)  직접 사용하여 서버에 장애가 발생해 다운되더라도 데이터가 손실 > 사용자가 많아질수록 부하가 심해지고 느려짐
    // > 사용자가 늘어날수록 db에 과부하가 옴 > Cache Server를 도입하여 사용함 > 이게 redis다


    //cache는 뭘까?
    // 한 번 읽어온 데이터를 임의의 공간에 저장하여 다시 읽어올 때 빠르게 결과값을 받을 수 있도록 도와주는 공간
    // 같은 요청이 여러 번 들어와도 매번 db를 거치는 것이 아닌 캐시 서버에서 첫 번째 요청 이후 저장된 결과값을 바로 내려주기 때문에 db의 부하를 줄이고 서비스 속도도 느려지지 않는 장점이 있음

    //Cache Server 흐름 공부하기

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    // Transaction 사용을 위해 redisTemplate를 이용한 방식을 적용한다.
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}