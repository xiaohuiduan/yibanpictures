var imageList = document.getElementsByTagName('li');
var spanArea;

//用户手指划过触发事件
function touchmoveEvent(){
  //手指划过，区域border增加样式，触碰到另一区域时解除
  for(var i=0; i<imageList.length; i++){
    spanArea = imageList[i].firstChild;
    spanArea.addEventListener('click', function(){
      touchmoveImageArea();
      e.style = "border:1px solid red;";
      console.log('1');
    },false);
    spanArea.addEventListener('swipe', function(){
      touchmoveLeaveImageArea();
    },false);
  }
}
//改变当前图片区域样式,增加边框阴影
function touchmoveImageArea(e){
  var event = e || window.event;
  event.style = "background-color: #f5f5f5;background:#000;border:1px solid #fff;border-width:5px 5px 5px 5px;box-shadow:1px 1px 5px #333;-webkit-box-shadow:1px 1px 5px #333;-moz-box-shadow:1px 1px 5px #333;width:200px;";
  if(event.preventDefault){
    event.preventDefault();
  }else{
    event.returnValue = false;
  }
}
//改变当前图片区域样式,恢复原状态
function touchmoveLeaveImageArea(e){
  var event = e || window.event;
  event.style = "background-color: #f5f5f5;";
  if(event.preventDefault){
    event.preventDefault();
  }else{
    event.returnValue = false;
  }
}

touchmoveEvent();


//使用jquery Alex异步读取
var jqueryNextPage = document.getElementsByClassName('next-page')[0];
