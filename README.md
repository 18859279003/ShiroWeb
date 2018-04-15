# shiroweb
根据shiro.ini配置，只要未登录则返回到/login的url，login不受限制，admin,student,teacher需要验证权限，根据web.xml中filter过滤器，每个url访问对应相应的方法，
shiro.ini中指明自名义realm文件，登录时完成验证和授权，对后续url提供权限和身份。
