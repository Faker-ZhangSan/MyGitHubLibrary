# MyGitHubLibrary
这个是我自己的依赖库，当前里面只添加了一个自定义视图，后续会一直更新和改进的。
使用步骤：
## 1、Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```        
  ## 2、 Add the dependency
 ```
  dependencies {
	        implementation 'com.github.alwaysRuo:MyGitHubLibrary:Tag'
	}
```
