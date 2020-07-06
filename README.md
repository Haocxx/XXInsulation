# XXInsulation
An Android Module Decouple Project

## Just Do It

### Integrate
Add follow rules to your proguard file.
```txt
-keep class com.haocxx.xxinsulation.annotation.Synthetic
```

### Runtime Initialize
You can initialize XXInsulation as follow, recommend add to {Application#onCreate}.
```Java
XXInsulationClient.getDefault().init();
```
### How To Use
First, you need to add your interface in base module, remember extends {IInsulator}.
```Java
public interface LoginInterface extends IInsulator {
  void login();
}
```
Then, implement interface in a decouple module, remember add {@Insulator} to it.
```Java
@Insulator
public class LoginImpl implements LoginInterface {
    @Override
    public void login() {
        Log.d("Haocxx_LoginImpl", "login");
    }
}
```
Last you need to define {#insulatorPackageName} in build.gradle of decouple module.  
ATTENTION: {#insulatorPackageName} must be unique for a module.  
Do not define a same {#insulatorPackageName} for two module.
```Gradle
android {
    ...
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += [insulatorPackageName: 'com.xxx.xxx', // unique module name
                ]
            }
        }
    }
    ...
}
```
Now you can use XXInsulation and interface to reach implement class.
```Java
XXInsulationClient.getDefault().getInsulator(LoginInterface.class).login();
```
And in logcat:
```txt
2020-05-29 18:58:30.714 31211-31211/? D/Haocxx_LoginImpl: login
```
Fucking perfect, yeah?

## Dependencies
[JavaPoet](https://github.com/square/javapoet)