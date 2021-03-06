
<!-- TOC -->

- [1. Spring Data Redis](#1-spring-data-redis)
  - [1.1. Redis 安装](#11-redis-安装)
  - [1.2. Spring Boot 整合 Redis](#12-spring-boot-整合-redis)
- [2. Redis基本操作](#2-redis基本操作)
  - [2.1. Key(键)](#21-key键)
  - [2.2. String(字符串)](#22-string字符串)
  - [2.3. Hash(哈希)](#23-hash哈希)
  - [2.4. List(列表)](#24-list列表)
  - [2.5. Set(集合)](#25-set集合)
  - [2.6. Sorted Set(有序集合)](#26-sorted-set有序集合)
  - [2.7. 事务](#27-事务)
- [3. Spring Data Redis](#3-spring-data-redis)
  - [3.1. RedisTemplate](#31-redistemplate)
  - [3.2. RedisOperations and RedisTemplate](#32-redisoperations-and-redistemplate)
  - [3.3. ValueOperations and BoundValueOperations](#33-valueoperations-and-boundvalueoperations)
  - [3.4. HashOperations and BoundHashOperations](#34-hashoperations-and-boundhashoperations)
  - [3.5. ListOperations and BoundListOperations](#35-listoperations-and-boundlistoperations)
  - [3.6. SetOperations and BoundSetOperations](#36-setoperations-and-boundsetoperations)
  - [3.7. ZSetOperations and BoundZSetOperations](#37-zsetoperations-and-boundzsetoperations)
  - [3.8. GeoOperations and BoundGeoOperations](#38-geooperations-and-boundgeooperations)
  - [3.9. HyperLogLogOperations](#39-hyperloglogoperations)
- [4. 事务](#4-事务)
  - [4.1. 编程事务](#41-编程事务)
  - [4.2. 声明事务](#42-声明事务)

<!-- /TOC -->

# 1. Spring Data Redis
## 1.1. Redis 安装
## 1.2. Spring Boot 整合 Redis

第一步: pom.xml添加依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```
第二步: 添加配置
```yml
spring:
  # REDIS (RedisProperties)
  redis:
    # Redis数据库索引（默认为0）
    database: 0
    # Redis服务器地址
    host: 127.0.0.1
    # Redis服务器连接端口
    port: 6379
    # Redis服务器连接密码（默认为空）
    password: '*****'
    # 连接超时时间（毫秒）
    timeout: 0
    pool:
      # 连接池最大连接数（使用负值表示没有限制）
      max-active: 8
      # 连接池中的最大空闲连接
      max-idle: 8
      # 连接池最大阻塞等待时间（使用负值表示没有限制）
      max-wait: -1
      # 连接池中的最小空闲连接
      min-idle: 0
```
第三步: 写一个demo试试
```java
@Component
public class HelloWorldRedisClient {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public Object helloWorld() {
        ValueOperations<Object, Object> objectObjectValueOperations = redisTemplate.opsForValue();
        objectObjectValueOperations.set("hello", "helloWorld");
        Object hello = objectObjectValueOperations.get("hello");
        return hello;
    }
}
```
# 2. Redis基本操作
## 2.1. Key(键)
## 2.2. String(字符串)
## 2.3. Hash(哈希)
## 2.4. List(列表)
## 2.5. Set(集合)
## 2.6. Sorted Set(有序集合)
## 2.7. 事务

# 3. Spring Data Redis

## 3.1. RedisTemplate
org.springframework.data.redis.core包下提供可丰富的接口进行操作,

`RedisTemplate`的操作集合

> 按键操作

| Interface(Key Type Operations) | Interface(Key Bound Operations) | Description                                      |
| ------------------------------ | ------------------------------- | ------------------------------------------------ |
| GeoOperations                  | BoundGeoOperations              | Redis的地理空间操作的，比如`GEOADD`，`GEORADIUS` |
| HashOperations                 | BoundHashOperations             | hash操作                                         |
| ListOperations                 | BoundListOperations             | list操作                                         |
| SetOperations                  | BoundSetOperations              | set操作                                          |
| ValueOperations                | BoundValueOperations            | string操作                                       |
| ZSetOperations                 | BoundZSetOperations             | sorted set 操作                                  |
| StreamOperations               | BoundStreamOperations           | Stream 操作                                      |
| HyperLogLogOperations          | -                               | HyperLogLog操作，例如`PFADD`，`PFCOUNT`          |
| -                              | BoundKeyOperations              | 绑定key操作                                      |



## 3.2. RedisOperations and RedisTemplate

| RedisOperations                                                                               | Redis Key                                                      | Description                                                                                                                                                                         |
| --------------------------------------------------------------------------------------------- | -------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Boolean delete(K key)<br>Long delete(Collection<K> keys)                                      | DEL key                                                        | 该命令用于在 key 存在时删除 key                                                                                                                                                     |
| byte[] dump(K key)                                                                            | DUMP key                                                       | 序列化给定 key ，并返回被序列化的值                                                                                                                                                 |
| Boolean hasKey(K key)<br>Long countExistingKeys(Collection<K> keys)                           | EXISTS key                                                     | 检查给定 key 是否存在                                                                                                                                                               |
| Boolean expire(K key, long timeout, TimeUnit unit)<br>Boolean expire(K key, Duration timeout) | EXPIRE key seconds<br>PEXPIRE key milliseconds                 | 为给定 key 设置过期时间，以秒计<br> 设置 key 的过期时间以毫秒计                                                                                                                     |
| Boolean expireAt(K key, Date date)<br>Boolean expireAt(K key, Instant expireAt)               | EXPIREAT key timestamp<br>PEXPIREAT key milliseconds-timestamp | EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置过期时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)<br>设置 key 过期时间的时间戳(unix timestamp) 以毫秒计 |
| Set<K> keys(K pattern)                                                                        | KEYS pattern                                                   | 查找所有符合给定模式( pattern)的 key                                                                                                                                                |
| Boolean move(K key, int dbIndex)                                                              | MOVE key db                                                    | 将当前数据库的 key 移动到给定的数据库 db 当中                                                                                                                                       |
| Boolean persist(K key)                                                                        | PERSIST key                                                    | 移除 key 的过期时间，key 将持久保持                                                                                                                                                 |
| Long getExpire(K key)<br>Long getExpire(K key, TimeUnit timeUnit)                             | PTTL key<br>TTL key                                            | 以毫秒为单位返回 key 的剩余的过期时间<br> 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)                                                                                |
| K randomKey()                                                                                 | RANDOMKEY                                                      | 从当前数据库中随机返回一个 key                                                                                                                                                      |
| void rename(K oldKey, K newKey)                                                               | RENAME key newkey                                              | 修改 key 的名称                                                                                                                                                                     |
| Boolean renameIfAbsent(K oldKey, K newKey)                                                    | RENAMENX key newkey                                            | 仅当 newkey 不存在时，将 key 改名为 newkey                                                                                                                                          |
| DataType type(K key)                                                                          | TYPE key                                                       | 返回 key 所储存的值的类型                                                                                                                                                           |
| void multi()                                                                                  | MULTI                                                          | 标记一个事务块的开始                                                                                                                                                                |
| List<Object> exec()                                                                           | EXEC                                                           | 执行所有事务块内的命令                                                                                                                                                              |
| void discard()                                                                                | DISCARD                                                        | 取消事务，放弃执行事务块内的所有命令                                                                                                                                                |



## 3.3. ValueOperations and BoundValueOperations
`org.springframework.data.redis.core.ValueOperations`和`org.springframework.data.redis.core.BoundValueOperations`是对string类型的操作

| ValueOperations                                                                                                                     | BoundValueOperations                                                                                                  | Redis String                                             | Description                                                                                         |
| ----------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- | --------------------------------------------------------------------------------------------------- |
| Integer append(K key, String value)                                                                                                 | Integer append(K key, String value)                                                                                   | APPEND key value                                         | 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾。  |
| List<Long> bitField(K key, BitFieldSubCommands subCommands)                                                                         | -                                                                                                                     | bitField                                                 |                                                                                                     |
| Long decrement(K key)                                                                                                               | Long decrement()                                                                                                      | DECR key                                                 | 将 key 中储存的数字值减一                                                                           |
| Long decrement(K key, long delta)                                                                                                   | Long decrement(long delta)                                                                                            | DECRBY key decrement                                     | key 所储存的值减去给定的减量值（decrement）                                                         |
| V get(Object key)                                                                                                                   | V get()                                                                                                               | GET key                                                  | 获取指定 key 的值                                                                                   |
| String get(K key, long start, long end)                                                                                             | V getAndSet(V value)                                                                                                  | GETRANGE key start end                                   | 返回 key 中字符串值的子字符                                                                         |
| V getAndSet(K key, V value)                                                                                                         | V getAndSet(V value)                                                                                                  | GETSET key value                                         | 将给定 key 的值设为 value ，并返回 key 的旧值(old value)                                            |
| Boolean getBit(K key, long offset)                                                                                                  |                                                                                                                       | GETBIT key offset                                        | 对 key 所储存的字符串值，获取指定偏移量上的位(bit)                                                  |
| Long increment(K key);                                                                                                              | Long increment()                                                                                                      | INCR key                                                 | 将 key 中储存的数字值增一                                                                           |
| Long increment(K key, long delta)                                                                                                   | Long increment(long delta)                                                                                            | INCRBY key increment                                     | 将 key 所储存的值加上给定的增量值（increment)                                                       |
| Double increment(K key, double delta)                                                                                               | Double increment(double delta)                                                                                        | INCRBYFLOAT key increment                                | 将 key 所储存的值加上给定的浮点增量值（increment)                                                   |
| List<V> multiGet(Collection<K> keys)                                                                                                | -                                                                                                                     | MGET key1 [key2..]                                       | 获取所有(一个或多个)给定 key 的值                                                                   |
| void multiSet(Map<? extends K, ? extends V> map)                                                                                    | -                                                                                                                     | MSET key value [key value ...]                           | 同时设置一个或多个 key-value 对                                                                     |
| Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map)                                                                         | -                                                                                                                     | MSETNX key value [key value ...]                         | 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在                                      |
| void set(K key, V value)                                                                                                            | void set(V value)                                                                                                     | SET key value                                            | 设置指定 key 的值                                                                                   |
| void set(K key, V value, long timeout, TimeUnit unit)<br> default void set(K key, V value, Duration timeout)                        | void set(V value, long timeout, TimeUnit unit)<br>default void set(V value, Duration timeout)                         | SETEX key seconds value<br>PSETEX key milliseconds value | 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位),milliseconds(毫秒)             |
| void set(K key, V value, long offset)                                                                                               | void set(V value, long offset)                                                                                        | SETRANGE key offset value                                | 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始                                    |
| Boolean setBit(K key, long offset, boolean value)                                                                                   | -                                                                                                                     | SETBIT key offset value                                  | 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)                                            |
| Boolean setIfAbsent(K key, V value)                                                                                                 | Boolean setIfAbsent(V value)                                                                                          | SETNX key value                                          | 只有在 key 不存在时设置 key 的值                                                                    |
| Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit)<br>default Boolean setIfAbsent(K key, V value, Duration timeout)   | Boolean setIfAbsent(V value, long timeout, TimeUnit unit)<br>default Boolean setIfAbsent(V value, Duration timeout)   | SET key value                                            | 只有在 key 不存在时设置 key 的值 。并将 key 的过期时间设为 seconds (以秒为单位) ,milliseconds(毫秒) |
| Boolean setIfPresent(K key, V value)                                                                                                | Boolean setIfPresent(V value)                                                                                         | SET key value                                            | 只有在 key 存在设置 key 的值                                                                        |
| Boolean setIfPresent(K key, V value, long timeout, TimeUnit unit)<br>default Boolean setIfPresent(K key, V value, Duration timeout) | Boolean setIfPresent(V value, long timeout, TimeUnit unit)<br>default Boolean setIfPresent(V value, Duration timeout) | SET key value                                            | 只有在 key 存在时设置 key 的值 。并将 key 的过期时间设为 seconds (以秒为单位) ,milliseconds(毫秒)   |
| Long size(K key)                                                                                                                    | Long size()                                                                                                           | STRLEN key                                               | 返回 key 所储存的字符串值的长度                                                                     |


## 3.4. HashOperations and BoundHashOperations
| HashOperations                                             | BoundHashOperations                                 | Redis Hash                                     | Description                                           |
| ---------------------------------------------------------- | --------------------------------------------------- | ---------------------------------------------- | ----------------------------------------------------- |
| Long delete(H key, Object... hashKeys)                     | Long delete(Object... keys)                         | HDEL key field1 [field2]                       | 删除一个或多个哈希表字段                              |
| Map<HK, HV> entries(H key)                                 | Map<HK, HV> entries()                               | HGETALL key                                    | 获取在哈希表中指定 key 的所有字段和值                 |
| HV get(H key, Object hashKey)                              | HV get(Object member)                               | HGET key field                                 | 获取存储在哈希表中指定字段的值                        |
| Boolean hasKey(H key, Object hashKey)                      | Boolean hasKey(H key, Object hashKey)               | HEXISTS key field                              | 查看哈希表 key 中，指定的字段是否存在                 |
| Long increment(H key, HK hashKey, long delta)              | Long increment(HK key, long delta)                  | HINCRBY key field increment                    | 为哈希表 key 中的指定字段的整数值加上增量 increment   |
| Double increment(H key, HK hashKey, double delta)          | Double increment(HK key, double delta)              | HINCRBYFLOAT key field increment               | 为哈希表 key 中的指定字段的浮点数值加上增量 increment |
| Set<HK> keys(H key)                                        | Set<HK> keys()                                      | HKEYS key                                      | 获取所有哈希表中的字段                                |
| Long lengthOfValue(H key, HK hashKey)                      | Long lengthOfValue(HK hashKey)                      | -                                              | 获取对应key的指定字段的长度                           |
| List<HV> multiGet(H key, Collection<HK> hashKeys)          | List<HV> multiGet(Collection<HK> keys)              | HMGET key field1 [field2]                      | 获取所有给定字段的值                                  |
| void put(H key, HK hashKey, HV value)                      | void put(HK key, HV value)                          | HSET key field value                           | 将哈希表 key 中的字段 field 的值设为 value            |
| void putAll(H key, Map<? extends HK, ? extends HV> m)      | void putAll(Map<? extends HK, ? extends HV> m)      | HMSET key field1 value1 [field2 value2 ]       | 同时将多个 field-value (域-值)对设置到哈希表 key 中   |
| Boolean putIfAbsent(H key, HK hashKey, HV value)           | Boolean putIfAbsent(HK key, HV value)               | HSETNX key field value                         | 只有在字段 field 不存在时，设置哈希表字段的值         |
| Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options) | Cursor<Map.Entry<HK, HV>> scan(ScanOptions options) | HSCAN key cursor [MATCH pattern] [COUNT count] | 迭代哈希表中的键值对                                  |
| Long size(H key)                                           | Long size()                                         | HLEN key                                       | 获取哈希表中字段的数量                                |
| List<HV> values(H key)                                     | List<HV> values()                                   | HVALS key                                      | 获取哈希表中所有值                                    |


## 3.5. ListOperations and BoundListOperations

| ListOperations                                                                                                                                                       | BoundListOperations                                                              | Redis List                              | Description                                                                                                               |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------- | --------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| V index(K key, long index)                                                                                                                                           | V index(long index)                                                              | LINDEX key index                        | 通过索引获取列表中的元素                                                                                                  |
| V leftPop(K key)                                                                                                                                                     | V leftPop()                                                                      | LPOP key                                | 移出并获取列表的第一个元素                                                                                                |
| V leftPop(K key, long timeout, TimeUnit unit)<br>default V leftPop(K key, Duration timeout)                                                                          | V leftPop(long timeout, TimeUnit unit)<br>default V leftPop(Duration timeout)    | BLPOP key1 [key2 ] timeout              | 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止                                   |
| Long leftPush(K key, V value)<br> Long leftPushAll(K key, V... values)<br>Long leftPushAll(K key, Collection<V> values)                                              | Long leftPush(V value)<br>Long leftPushAll(V... values)                          | LPUSH key value1 [value2]               | 将一个或多个值插入到列表头部                                                                                              |
| Long leftPush(K key, V pivot, V value)                                                                                                                               | Long leftPush(V pivot, V value)                                                  | LINSERT key BEFORE \| AFTER pivot value | 在列表的元素前或者后插入元素                                                                                              |
| Long leftPushIfPresent(K key, V value)                                                                                                                               | Long leftPushIfPresent(V value)                                                  | LPUSHX key value                        | 将一个值插入到已存在的列表头部                                                                                            |
| List<V> range(K key, long start, long end)                                                                                                                           | List<V> range(long start, long end)                                              | LRANGE key start stop                   | 获取列表指定范围内的元素                                                                                                  |
| Long remove(K key, long count, Object value)                                                                                                                         | Long remove(K key, long count, Object value)                                     | LREM key count value                    | 移除列表元素                                                                                                              |
| V rightPop(K key)<br>Long rightPushAll(K key, V... values)<br>Long rightPushAll(K key, Collection<V> values)                                                         | V rightPop()<br>Long rightPushAll(V... values)                                   | RPOP key                                | 移除列表的最后一个元素，返回值为移除的元素                                                                                |
| V rightPop(K key, long timeout, TimeUnit unit)<br>default V rightPop(K key, Duration timeout)                                                                        | V rightPop(long timeout, TimeUnit unit)<br> default V rightPop(Duration timeout) | BRPOP key1 [key2 ] timeout              | 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止                                 |
| Long rightPush(K key, V value)                                                                                                                                       | Long rightPush(V value)                                                          | RPUSH key value1 [value2]               | 在列表中添加一个或多个值                                                                                                  |
| Long rightPush(K key, V pivot, V value)                                                                                                                              | Long rightPush(V pivot, V value)                                                 | LINSERT key BEFORE                      | 在列表的元素前或者后插入元素                                                                                              |  |
| V rightPopAndLeftPush(K sourceKey, K destinationKey)                                                                                                                 | -                                                                                | RPOPLPUSH source destination            | 移除列表的最后一个元素，并将该元素添加到另一个列表并返回                                                                  |
| V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit)<br> default V rightPopAndLeftPush(K sourceKey, K destinationKey, Duration timeout) | -                                                                                | BRPOPLPUSH source destination timeout   | 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止 |
| Long rightPushIfPresent(K key, V value)                                                                                                                              | Long rightPushIfPresent(V value)                                                 | RPUSHX key value                        | 为已存在的列表添加值                                                                                                      |
| void set(K key, long index, V value)                                                                                                                                 | void set(long index, V value)                                                    | LSET key index value                    | 通过索引设置列表元素的值                                                                                                  |
| Long size(K key)                                                                                                                                                     | Long size(K key)                                                                 | LLEN key                                | LLEN key                                                                                                                  |
| void trim(K key, long start, long end)                                                                                                                               | void trim(K key, long start, long end)                                           | LTRIM key start stop                    | 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除                          |




## 3.6. SetOperations and BoundSetOperations

| SetOperations                                                                                                                                                                         | BoundSetOperations                                                                                 | Redis List                                     | Description                                         |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------- | ---------------------------------------------- | --------------------------------------------------- |
| Long add(K key, V... values)                                                                                                                                                          | Long add(V... values)                                                                              | SADD key member1 [member2]                     | 向集合添加一个或多个成员                            |
| Set<V> difference(K key, K otherKey)<br>Set<V> difference(K key, Collection<K> otherKeys)<br> Set<V> difference(Collection<K> keys)                                                   | Set<V> diff(K key)<br>Set<V> diff(Collection<K> keys)                                              | SDIFF key1 [key2]                              | 返回给定所有集合的差集                              |
| Long differenceAndStore(K key, K otherKey, K destKey)<br>Long differenceAndStore(K key, Collection<K> otherKeys, K destKey)<br>Long differenceAndStore(Collection<K> keys, K destKey) | void diffAndStore(K keys, K destKey)<br>void diffAndStore(Collection<K> keys, K destKey)           | SDIFFSTORE destination key1 [key2]             | 返回给定所有集合的差集并存储在 destination 中       |
| Set<V> distinctRandomMembers(K key, long count)                                                                                                                                       | Set<V> distinctRandomMembers(long count)                                                           | SRANDMEMBER key [count]                        | 返回集合中一个或多个成员                            |
| Set<V> intersect(K key, K otherKey)<br>Set<V> intersect(K key, Collection<K> otherKeys)<br>Set<V> intersect(Collection<K> keys)                                                       | Set<V> intersect(K key)<br>  Set<V> intersect(Collection<K> keys)                                  | SINTER key1 [key2]                             | 返回给定所有集合的交集                              |
| Long intersectAndStore(K key, K otherKey, K destKey)<br>Long intersectAndStore(K key, Collection<K> otherKeys, K destKey)<br>Long intersectAndStore(Collection<K> keys, K destKey)    | void intersectAndStore(K key, K destKey)<br> void intersectAndStore(Collection<K> keys, K destKey) | SINTERSTORE destination key1 [key2]            | 返回给定所有集合的交集并存储在 destination 中       |
| Boolean isMember(K key, Object o)                                                                                                                                                     | Boolean isMember(Object o)                                                                         | SISMEMBER key member                           | 判断 member 元素是否是集合 key 的成员               |
| Set<V> members(K key)                                                                                                                                                                 | Set<V> members()                                                                                   | SMEMBERS key                                   | 返回集合中的所有成员                                |
| Boolean move(K key, V value, K destKey)                                                                                                                                               | Boolean move(K key, V value, K destKey)                                                            | SMOVE source destination member                | 将 member 元素从 source 集合移动到 destination 集合 |
| V pop(K key)<br>List<V> pop(K key, long count)                                                                                                                                        | V pop()                                                                                            | SPOP key                                       | 移除并返回集合中的一个随机元素                      |
| V randomMember(K key)<br>List<V> randomMembers(K key, long count)                                                                                                                     | V randomMember()<br>List<V> randomMembers(long count)                                              | SRANDMEMBER key [count]                        | 返回集合中一个或多个随机数                          |
| Long remove(K key, Object... values)                                                                                                                                                  | Long remove(Object... values)                                                                      | SREM key member1 [member2]                     | 移除集合中一个或多个成员                            |
| Cursor<V> scan(K key, ScanOptions options)                                                                                                                                            | Cursor<V> scan(ScanOptions options)                                                                | SSCAN key cursor [MATCH pattern] [COUNT count] | 迭代集合中的元素                                    |
| Long size(K key)                                                                                                                                                                      | Long size()                                                                                        | SCARD key                                      | 获取集合的成员数                                    |
| Set<V> union(K key, K otherKey)<br>Set<V> union(K key, Collection<K> otherKeys)<br>Set<V> union(Collection<K> keys)                                                                   | Set<V> union(K key)<br>Set<V> union(Collection<K> keys)                                            | SUNION key1 [key2]                             | 返回所有给定集合的并集                              |
| Long unionAndStore(K key, K otherKey, K destKey)<br>Long unionAndStore(K key, Collection<K> otherKeys, K destKey)<br>Long unionAndStore(Collection<K> keys, K destKey)                | void unionAndStore(K key, K destKey)<br>void unionAndStore(Collection<K> keys, K destKey)          | SUNIONSTORE destination key1 [key2]            | 所有给定集合的并集存储在 destination 集合中         |


## 3.7. ZSetOperations and BoundZSetOperations

| ZSetOperations                                                                                                                                                                                                                                                                                                                 | BoundZSetOperations                                                                                                                                                                                                                                                                                | sorted set                                     | Description                                                         |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------- | ------------------------------------------------------------------- |
| Boolean add(K key, V value, double score)<br>Long add(K key, Set<TypedTuple<V>> tuples)                                                                                                                                                                                                                                        | Boolean add(V value, double score)<br>Long add(Set<TypedTuple<V>> tuples)                                                                                                                                                                                                                          | ZADD key score1 member1 [score2 member2]       | 向有序集合添加一个或多个成员，或者更新已存在成员的分数              |
| Long count(K key, double min, double max)                                                                                                                                                                                                                                                                                      | Long count(double min, double max)                                                                                                                                                                                                                                                                 | ZCOUNT key min max                             | 计算在有序集合中指定区间分数的成员数                                |
| Double incrementScore(K key, V value, double delta)                                                                                                                                                                                                                                                                            | Double incrementScore(V value, double delta)                                                                                                                                                                                                                                                       | ZINCRBY key increment member                   | 有序集合中对指定成员的分数加上增量 increment                        |
| Long intersectAndStore(K key, K otherKey, K destKey)<br>Long intersectAndStore(K key, Collection<K> otherKeys, K destKey)<br>Long intersectAndStore(K key, Collection<K> otherKeys, K destKey, Aggregate aggregate)<br>Long intersectAndStore(K key, Collection<K> otherKeys, K destKey, Aggregate aggregate, Weights weights) | Long intersectAndStore(K otherKey, K destKey)<br>Long intersectAndStore(Collection<K> otherKeys, K destKey)<br>Long intersectAndStore(Collection<K> otherKeys, K destKey, Aggregate aggregate)<br>Long intersectAndStore(Collection<K> otherKeys, K destKey, Aggregate aggregate, Weights weights) | ZINTERSTORE destination numkeys key [key ...]  | 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中 |
| Set<V> range(K key, long start, long end)                                                                                                                                                                                                                                                                                      | Set<V> range(long start, long end)                                                                                                                                                                                                                                                                 | ZRANGE key start stop [WITHSCORES]             | 通过索引区间返回有序集合指定区间内的成员                            |
| Set<V> rangeByLex(K key, Range range)<br>Set<V> rangeByLex(K key, Range range, Limit limit)                                                                                                                                                                                                                                    | Set<V> rangeByLex(Range range)<br>Set<V> rangeByLex(Range range, Limit limit)                                                                                                                                                                                                                      | ZRANGEBYLEX key min max [LIMIT offset count]   | 通过字典区间返回有序集合的成员                                      |
| Set<V> rangeByScore(K key, double min, double max)<br>Set<V> rangeByScore(K key, double min, double max, long offset, long count)                                                                                                                                                                                              | Set<V> rangeByScore(double min, double max)                                                                                                                                                                                                                                                        | ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT] | 通过分数返回有序集合指定区间内的成员                                |
| Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max)<br> Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count)                                                                                                                                                 | Set<TypedTuple<V>> rangeByScoreWithScores(double min, double max)                                                                                                                                                                                                                                  | ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT] | 通过分数返回有序集合指定区间内的成员                                |
| Set<TypedTuple<V>> rangeWithScores(K key, long start, long end)                                                                                                                                                                                                                                                                | Set<TypedTuple<V>> rangeWithScores(long start, long end)                                                                                                                                                                                                                                           | ZRANGE key start stop [WITHSCORES]             | 通过索引区间返回有序集合指定区间内的成员                            |
| Long rank(K key, Object o)                                                                                                                                                                                                                                                                                                     | Long rank(Object o)                                                                                                                                                                                                                                                                                | ZRANK key member                               | 返回有序集合中指定成员的索引                                        |
| Long remove(K key, Object... values)                                                                                                                                                                                                                                                                                           | Long remove(Object... values)                                                                                                                                                                                                                                                                      | ZREM key member [member ...]                   | 移除有序集合中的一个或多个成员                                      |
| Long removeRange(K key, long start, long end)                                                                                                                                                                                                                                                                                  | Long removeRange(long start, long end)                                                                                                                                                                                                                                                             | ZREMRANGEBYRANK key start stop                 | 移除有序集合中给定的排名区间的所有成员                              |
| Long removeRangeByScore(K key, double min, double max)                                                                                                                                                                                                                                                                         | Long removeRangeByScore(double min, double max)                                                                                                                                                                                                                                                    | ZREMRANGEBYSCORE key min max                   | 移除有序集合中给定的分数区间的所有成员                              |
| Set<V> reverseRange(K key, long start, long end)                                                                                                                                                                                                                                                                               | Set<V> reverseRange(long start, long end)                                                                                                                                                                                                                                                          | ZREVRANGE key start stop [WITHSCORES]          | 返回有序集中指定区间内的成员，通过索引，分数从高到低                |
| Set<V> reverseRangeByScore(K key, double min, double max)<br>Set<V> reverseRangeByScore(K key, double min, double max, long offset, long count)                                                                                                                                                                                | Set<V> reverseRangeByScore(double min, double max)                                                                                                                                                                                                                                                 | ZREVRANGEBYSCORE key max min [WITHSCORES]      | 返回有序集中指定分数区间内的成员，分数从高到低排序                  |
| Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max)<br>Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count)                                                                                                                                    | Set<TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max)                                                                                                                                                                                                                           | ZREVRANGEBYSCORE key max min [WITHSCORES]      | 返回有序集中指定分数区间内的成员，分数从高到低排序                  |
| Set<TypedTuple<V>> reverseRangeWithScores(K key, long start, long end)                                                                                                                                                                                                                                                         | Set<TypedTuple<V>> reverseRangeWithScores(long start, long end)                                                                                                                                                                                                                                    | ZREVRANGE key start stop [WITHSCORES]          | 返回有序集中指定区间内的成员，通过索引，分数从高到低                |
| Long reverseRank(K key, Object o)                                                                                                                                                                                                                                                                                              | Long reverseRank(Object o)                                                                                                                                                                                                                                                                         | ZREVRANK key member                            | 返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序  |
| Cursor<TypedTuple<V>> scan(K key, ScanOptions options)                                                                                                                                                                                                                                                                         | Cursor<TypedTuple<V>> scan(ScanOptions options)                                                                                                                                                                                                                                                    | ZSCAN key cursor [MATCH pattern] [COUNT count] | 迭代有序集合中的元素（包括元素成员和元素分值）                      |
| Double score(K key, Object o)                                                                                                                                                                                                                                                                                                  | Double score(Object o)                                                                                                                                                                                                                                                                             | ZSCORE key member                              | 返回有序集中，成员的分数值                                          |
| Long size(K key)                                                                                                                                                                                                                                                                                                               | Long size()                                                                                                                                                                                                                                                                                        | ZCARD key                                      | 获取有序集合的成员数                                                |
| Long unionAndStore(K key, K otherKey, K destKey)<br>Long unionAndStore(K key, Collection<K> otherKeys, K destKey)<br>Long unionAndStore(K key, Collection<K> otherKeys, K destKey, Aggregate aggregate)<br>unionAndStore(K key, Collection<K> otherKeys, K destKey, Aggregate aggregate, Weights weights)                      | Long unionAndStore(K otherKey, K destKey);<br>Long unionAndStore(Collection<K> otherKeys, K destKey)<br>Long unionAndStore(Collection<K> otherKeys, K destKey, Aggregate aggregate)<br>Long unionAndStore(Collection<K> otherKeys, K destKey, Aggregate aggregate, Weights weights)                | ZUNIONSTORE destination numkeys key [key ...]  | 计算给定的一个或多个有序集的并集，并存储在新的 key 中               |
| Long zCard(K key)                                                                                                                                                                                                                                                                                                              | Long zCard()                                                                                                                                                                                                                                                                                       | ZCARD key                                      | 获取有序集合的成员数                                                |


## 3.8. GeoOperations and BoundGeoOperations

## 3.9. HyperLogLogOperations

| HyperLogLogOperations                      | Redis HyperLogLog                         | Description                               |
| ------------------------------------------ | ----------------------------------------- | ----------------------------------------- |
| Long add(K key, V... values)               | PFADD key element [element ...]           | 添加指定元素到 HyperLogLog 中             |
| Long size(K... keys)                       | PFCOUNT key [key ...]                     | 返回给定 HyperLogLog 的基数估算值         |
| Long union(K destination, K... sourceKeys) | PFMERGE destkey sourcekey [sourcekey ...] | 将多个 HyperLogLog 合并为一个 HyperLogLog |
| void delete(K key)                         | -                                         | -                                         |


# 4. 事务
## 4.1. 编程事务

`Redis` 通过`multi`，`exec`和`discard`命令为`transactions`提供支持。这些操作可在`RedisTemplate`上获得。但是，`RedisTemplate`不保证使用相同的连接执行 `transaction` 中的所有操作
Spring Data Redis 提供`SessionCallback`接口，以便在需要使用相同的`connection`执行多个操作时使用，例如使用 Redis transactions 时。以下 example 使用`multi`方法

```java
//execute a transaction
List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
  public List<Object> execute(RedisOperations operations) throws DataAccessException {
    operations.multi();
    operations.opsForSet().add("key", "value1");

    // This will contain the results of all operations in the transaction
    return operations.exec();
  }
});
System.out.println("Number of items added to set: " + txResults.get(0));

```


## 4.2. 声明事务

默认情况下，transaction 支持已禁用，必须通过设置`setEnableTransactionSupport(true)`为每个正在使用的`RedisTemplate`显式启用。这样做会强制_将当前`RedisConnection`绑定到触发`MULTI`的当前`Thread`。
如果 transaction 完成且没有错误，则调用`EXEC`。否则调用`DISCARD`。进入`MULTI`后，`RedisConnection`队列写入操作。所有`readonly`操作(例如`KEYS`)都通过管道传输到新的(non-thread-bound)`RedisConnection`。

以下 example 显示了如何配置 transaction management

```java
@Configuration
@EnableTransactionManagement                                 // (1)配置 Spring Context 以启用声明性 transaction management
public class RedisTxContextConfiguration {

  @Bean
  public StringRedisTemplate redisTemplate() {
    StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
    // explicitly enable transaction support
    template.setEnableTransactionSupport(true);             // (2)通过 binding 与当前线程的连接，将RedisTemplate配置为参与 transactions
    return template;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    // jedis || Lettuce || srp || ...
  }

  @Bean
  public PlatformTransactionManager transactionManager() throws SQLException {
    return new DataSourceTransactionManager(dataSource());   // (3)Transaction management 需要PlatformTransactionManager。 Spring Data Redis 未附带PlatformTransactionManager implementation。假设您的 application 使用 JDBC，Spring Data Redis 可以使用现有的 transaction managers 参与 transactions
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    // ...
  }
}
```

```java
// must be performed on thread-bound connection
template.opsForValue().set("thing1", "thing2");

// read operation must be executed on a free (not transaction-aware) connection
template.keys("*");

// returns null as values set within a transaction are not visible
template.opsForValue().get("thing1");
```
