$('#file1').on('change', function() {
    ajaxFileUpload();
});


function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

function appearPicture($picture) {
    var $pictureVessel = $('div.picture');
    var vesselWidth = $pictureVessel.width(),
        vesselHeight = $pictureVessel.height();
    var imgWidth = $picture.width,
        imgHeight = $picture.height;

    if (imgWidth < vesselWidth && imgHeight < vesselHeight) {
        $picture.width = imgWidth;
        $picture.height = imgHeight;
    } else {

        if (vesselWidth / vesselHeight < imgWidth / imgHeight) {
            $picture.width = vesselWidth;
            $picture.height = vesselWidth * (imgHeight / imgWidth);
        } else {
            $picture.width = vesselHeight * (imgWidth / imgHeight);
            $picture.height = vesselHeight;
        }
        $pictureVessel.append($picture);
    }
}

function userDataRequest() {
    var $picture = $('#file1');
    appearPicture($picture);
    var userData = new FormData();
    userData.append('file', $picture[0].files[0]);
    $(".picture").attr("src", getObjectURL($picture[0].files[0]))
    return userData;
}

function ajaxFileUpload() {
    var userData = userDataRequest();
    var load = new Loading();
    load.init();
    load.start();
    $.ajax({
        url: "/upload",
        type: "post",
        data: userData,

        // false --> 自动加上正确的Content-type
        contentType: false,

        // false --> 避开jQuery对userData默认处理
        //           XMLHttpRequest对userData正确处理
        processData: false,

        success: function(data) {
            load.stop();
            if (data.error_code == 0) {
                callBack.call(data);
            }
            if (data.error_code == "noFace") {
                alert("请上传清晰的人脸图片"); //没有点
            }
            if (data.error_code == "QPS") {
                alert("服务器繁忙");
            }
            if (data.error_code == "unKnow") {
                alert("未知错误");
            }
            if (data.error_code == "sizeLarge") {
                alert("图片过大( ⊙ o ⊙ )！");
            }

            $("upload").hide();
        },

        error: function() {
            load.stop();
            alert("上传失败！");

        }
    });
}
function callBack(data) {
    var info = document.getElementsByTagName('li');

    var jsonData = this.result.face_list[0];
    for (var i = 0; i < 8; i++) {
        switch (i) {
            case 0:
                switch (jsonData.gender.type) {
                    case "male":
                        info[i].children[0].innerHTML = ("男");
                        break;
                    case "female":
                        info[i].children[0].innerHTML = "女";
                        break;
                }
                break;

            case 1:
                info[i].children[0].innerHTML = (jsonData.age);
                break;

            case 2:
                switch (jsonData.race.type) {
                    case "yellow":
                        info[i].children[0].innerHTML = ("黄种人");
                        break;
                    case "white":
                        info[i].children[0].innerHTML = ("白种人");
                        break;
                    case "black":
                        info[i].children[0].innerHTML = ("黑种人");
                        break;
                    case "arabs":
                        info[i].children[0].innerHTML = ("阿拉伯人");
                        break;
                }
                break;

            case 3:
                info[i].children[0].innerHTML = (jsonData.beauty);
                break;

            case 4:
                switch (jsonData.expression.type) {
                    case "none":
                        info[i].children[0].innerHTML = ("无表情");
                        break;
                    case "smile":
                        info[i].children[0].innerHTML = ("微笑");
                        break;
                    case "laugh":
                        info[i].children[0].innerHTML = ("大笑");
                        break;
                }
                break;

            case 5:
                switch (jsonData.emotion.type) {
                    case "angry":
                        info[i].children[0].innerHTML = ("生气");
                        break;
                    case "disgust":
                        info[i].children[0].innerHTML = ("厌恶");
                        break;
                    case "fear":
                        info[i].children[0].innerHTML = ("恐惧");
                        break;
                    case "happy":
                        info[i].children[0].innerHTML = ("开心");
                        break;
                    case "sad":
                        info[i].children[0].innerHTML = ("悲伤");
                        break;
                    case "surprise":
                        info[i].children[0].innerHTML = ("惊讶");
                        break;
                    case "neutral":
                        info[i].children[0].innerHTML = ("无情绪");
                        break;
                }
                break;

            case 6:
                switch (jsonData.face_shape.type) {
                    case "square":
                        info[i].children[0].innerHTML = ("方形");
                        break;
                    case "triangle":
                        info[i].children[0].innerHTML = ("三角形");
                        break;
                    case "oval":
                        info[i].children[0].innerHTML = ("椭圆形");
                        break;
                    case "heart":
                        info[i].children[0].innerHTML = ("心形");
                        break;
                    case "round":
                        info[i].children[0].innerHTML = ("圆形");
                        break;
                }
                break;


            case 7:
                // $('li:last-child').addClass('glass');
                switch (jsonData.glasses.type) {
                    case "none":
                        info[i].children[0].innerHTML = "无眼镜";
                        break;
                    case "common":
                        info[i].children[0].innerHTML = ("普通眼镜");
                        break;
                    case "sun":
                        info[i].children[0].innerHTML = ("墨镜");
                        break;
                }
                break;
        }
    }
}