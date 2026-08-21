CREATE USER athena WITH PASSWORD 'athena';
CREATE DATABASE herodotus_thingsbrain OWNER athena;
GRANT ALL PRIVILEGES ON DATABASE herodotus_thingsbrain TO athena;


CREATE DATABASE herodotus_thingsbrain OWNER athena;
GRANT ALL PRIVILEGES ON DATABASE herodotus_thingsbrain TO athena;


## opengass

### [1] 以操作系统用户omm登录数据库主节点

```shell
su -omm
```

### [2]连接数据库

数据库安装完成后，默认生成名称为 `postgres` 的数据库。第一次连接数据库时可以连接到此数据库

```shell
gsql -d postgres
```

其中 `postgres` 为需要连接的数据库名称。另外，也可以使用如下任一命令连接数据库。

```shell
gsql postgres://omm:Gauss_234@127.0.0.1:8000/postgres -r
gsql -d "host=127.0.0.1 port=8000 dbname=postgres user=omm password=Gauss_234"
```

连接成功后，系统显示类似如下信息：

```shell
gsql ((openGauss 2.1.0 build 590b0f8e) compiled at 2021-09-30 14:29:04 commit 0 last mr  )
Non-SSL connection (SSL connection is recommended when requiring high-security)
Type "help" for help.

openGauss=# 
```

omm用户是管理员用户，因此系统显示“DBNAME=#”。若使用普通用户身份登录和连接数据库，系统显示“DBNAME=>”。

“Non-SSL connection”表示未使用SSL方式连接数据库。如果需要高安全性时，请使用SSL连接。

### [3]创建数据库用户。

默认只有 openGauss 安装时创建的管理员用户可以访问初始数据库，您还可以创建其他数据库用户帐号。如果使用如下命令创建用户，请记得修改密码，openGauss的默认密码规则为：至少包含英文大小写、数字、特殊符号中的3类不同的字符组合。

```shell
CREATE USER athena WITH PASSWORD 'athena!QAZ';
```

当结果显示为如下信息，则表示创建成功。

```shell
CREATE ROLE
```

如上创建了一个用户名为joe，密码为xxxxxxxxx的用户。

### [4]管理用户及权限。

如下命令为设置 athena 用户为系统管理员。

```shell
GRANT ALL PRIVILEGES TO athena;
```

### [5] 创建数据库。

```shell
CREATE DATABASE herodotus_athena_reactive OWNER athena;
```

当结果显示为如下信息，则表示创建成功。

```shell
CREATE DATABASE
```