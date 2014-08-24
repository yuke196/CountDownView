CountDownView
===
===
###what is this

show a **`android TextView`** which can count down time

===
###useage

* #####configuration

- if you are using android studio ,you can use this view with follow simple config:

config your dependencies in your app module as this

    dependencies {
               compile 'com.sharyuke.view:sharyuke-view:0.0.4'
           }


- if you are using eclipse , you can [download](http://search.maven.org/remotecontent?filepath=com/sharyuke/view/sharyuke-view/0.0.4/sharyuke-view-0.0.4.jar) jar file ,and put it into your libs directory.



* #####code in java file

start counting

`````
    CountDownView mCountDownView;
    mCountDownView = (CountDownView)findViewById(R.id.xxxx);
    mCountDownView.setUnits("day", "hour", "minute", "second");
    mCountDownView.setCountTime(1000);
    mCountDownView.startCount();
       
``````

  stop counting
      
  
``````
    mCountDownView.stopCount();
 
``````
 callBack
 
`````
mCountDownView.setOnCountOverListener(new CountDownView.CountOver() {
      @Override
      public void onCountOver() {
		//something to do
      }
    });
    
`````
 
 
anchor's homePage :[yuke](http://sharyuke.com)


* tips: the newest version is 0.0.4



