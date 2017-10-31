# PasswordinputView
支付密码输入框及支付键盘
## ScreenShot
InputPasswordDialog
<img src="display/Screenshot_1.png" width = "270" height = "480" alt="InputPasswordDialog" align=center />

PasswordInputView
<img src="display/Screenshot_2.png" width = "270" height = "480" alt="PasswordInputView" align=center />

## Usage

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.sychaos:PasswordinputView:1.0.1'
	}


## Sample Code
```xml
    <cloudist.cc.library.view.PasswordInputView
        android:id="@+id/password_inputview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/white"
        app:borderWidth="0.5dp"
        app:itemHeight="36dp"
        app:itemPadding="8dp"
        app:passwordLength="7"
        app:borderRadius="1dp"
        app:passwordWidth="6dp" />
```

```Java
  // bind keyBoardDialog to PasswordInputView
  final PasswordInputView passwordInputView = findViewById(R.id.password_inputview);
  passwordInputView.bindKeyBoard(getSupportFragmentManager(), "");
```

```Java
   // show InputPasswordDialog

   InputPasswordDialog.newInstance()
            .setTextChangeListener(new TextChangeListener() {
                @Override
                public void textChange(String text) {
                    //TODO
                }
            }).show(getSupportFragmentManager(), "");
```
