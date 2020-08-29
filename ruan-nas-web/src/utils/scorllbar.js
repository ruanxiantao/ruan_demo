export function scorllbar(boxc, hbox) {	//Y轴滚动
  let pointX, pointY, top = 0, moveX, moveY;
  var move = false;
  let box = $(boxc);
  var H = box.height();
  var W = box.width();
  var ht = $(hbox).height();
  var cententH = box.children().height();
  var btnH = H / cententH * H;
  let idCode = new Date().getTime();
  let scrollY = '<div class="scrollboxs' + idCode + '" style="width:8px;position:absolute;top:' + ht + 'px;right:0px;z-index:20;background-color:transparent;height:' + H + 'px;">';
  scrollY += '<div id="scrollbtn' + idCode + '" style="width:100%;height:' + btnH + 'px;margin:0 auto;position:absolute;cursor:pointer" tops="0">';
  scrollY += '<div style="background-color:rgba(144, 147, 153, 0.3);position:absolute;width:6px;left:0px;height:100%;border-radius:4px"></div>';
  scrollY += '</div></div>';
  box.parent().css({"position": "relative"});
  // console.log(box.height())
  // console.log(ht)
  if (H / cententH < 1) {
    $(".scrollboxs" + idCode).remove();
    box.before(scrollY);
    box.css({"overflow-y": "hidden"});
  } else {
    $(".scrollboxs" + idCode).remove();
  }
  $("#scrollbtn" + idCode).hover(function () {
    $(this).children().css({
      "background-color": "rgba(144, 147, 153, 0.3)",
    });
  }, function () {
    $(this).children().css({
      "background-color": "rgba(144, 147, 153, 0.3)",
    });
  });

  function addscroll(idCode) {

    top = $("#scrollbtn" + idCode).attr("tops");


    if (window.navigator.userAgent.toLowerCase().indexOf('firefox') != -1) { //火狐浏览器
      box.on('DOMMouseScroll', function (e, delta) {
        e.preventDefault();
        top = $("#scrollbtn" + idCode).attr("tops") * 1;

        var delta = e.detail;

        if (delta > 0) {
          if (top + btnH < H) {
            $("#scrollbtn" + idCode).css({
              "margin-top": (top + 10) + 'px'
            });
            $("#scrollbtn" + idCode).attr("tops", top + 10);
            box.scrollTop((top + 10) / H * cententH);
          }

        } else {
          if (top != 0) {
            $("#scrollbtn" + idCode).css({
              "margin-top": (top - 10) + 'px'
            });
            $("#scrollbtn" + idCode).attr("tops", top - 10);
            box.scrollTop((top - 10) / H * cententH);
          }
        }
      })
    } else { //其他浏览器
      box.on('mousewheel', function (e, delta) {
        // debugger
        e.preventDefault();
        // e.stopPropagation();
        top = $("#scrollbtn" + idCode).attr("tops") * 1;
        var delta = e.originalEvent.wheelDelta;
        if (delta < 0) {
          if (top + btnH < H) {
            $("#scrollbtn" + idCode).css({
              "margin-top": (top + 10) + 'px'
            });
            $("#scrollbtn" + idCode).attr("tops", top + 10);
            box.scrollTop((top + 10) / H * cententH);
          }

        } else {
          if (top != 0) {
            $("#scrollbtn" + idCode).css({
              "margin-top": (top - 10) + 'px'
            });
            $("#scrollbtn" + idCode).attr("tops", top - 10);
            box.scrollTop((top - 10) / H * cententH);
          }
        }

      })
    }
  }

  addscroll(idCode);
  $("#scrollbtn" + idCode).mousedown(function (e) {
    pointX = e.pageX;
    //这里可以得到鼠标Y坐标
    pointY = e.pageY;
    move = true;
    top = $("#scrollbtn" + idCode).attr("tops") * 1;
    $(this).focus();
  });
  $(document).mousemove(function (e) {
    e.stopPropagation();
    if (move == true) {
      box.unbind();
      moveX = e.pageX - pointX;
      //这里可以得到鼠标Y坐标
      moveY = e.pageY - pointY;
      $("#scrollbtn" + idCode).css({
        "margin-top": top + moveY
      });
      $("#scrollbtn" + idCode).attr("tops", top + moveY);
      box.scrollTop((top + moveY) / H * cententH);
      if (top + moveY > H - btnH) {
        $("#scrollbtn" + idCode).css({
          "margin-top": H - btnH
        });
        $("#scrollbtn" + idCode).attr("tops", H - btnH);
      } else if (top + moveY < 0) {
        $("#scrollbtn" + idCode).css({
          "margin-top": 0
        });
        $("#scrollbtn" + idCode).attr("tops", 0);
      }
    }
  });
  $(document).mouseup(function (e) {
    move = false;
  });

}
