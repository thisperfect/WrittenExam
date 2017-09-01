$('.hbt').on('click', function() {
    $('.high1').hide();
    $('.high2').show();
});
$('.hbt2').on('click', function() {
    $('.high1').show();
    $('.high2').hide();
});

//alert 重写
window.alert = function(str, strname, style, but) {
    var shield = document.createElement("DIV");
    shield.id = "shield";
    shield.style.position = "fixed";
    shield.style.left = "0";
    shield.style.top = "0";
    shield.style.width = "100%";
    shield.style.height = "100%";
    shield.style.marginLeft = "0";
    shield.style.marginTop = "0";
    shield.style.zIndex = "25";
    shield.style.background = "rgba(0,0,0,0.7)";
    var alertFram = document.createElement("DIV");
    alertFram.id = "alertFram";
    alertFram.style.position = "fixed";
    alertFram.style.width = "100%";
    alertFram.style.height = "100%";
    alertFram.style.left = "0";
    alertFram.style.top = "0";
    alertFram.style.marginLeft = "0";
    alertFram.style.marginTop = "0";
    alertFram.style.textAlign = "center";
    alertFram.style.lineHeight = "150px";
    alertFram.style.zIndex = "300";
    var strHtml = "";
    if (style) {
        strHtml += '<style>' + style + '</style>';
    }
    strHtml += '<ul class="ul">';
    strHtml += '<li class="title">';
    strHtml += '' + strname + '';
    strHtml += '<div class="closebt" onclick="closeThis()"></div>';
    strHtml += '</li>';
    strHtml += '<li class="alertcontent"><img src="../static/images/jg.png"/>'
            + str + '</li>';
    if (but) {
        strHtml += '<li class="btn-wrap"><div onclick="doOk()" class="btn"/>'
                + but.fir + '</div><div onclick="doOk()" class="btn2"/>'
                + but.sec + '</div></li>';
    }
    strHtml += '</ul>';
    // strHtml += '<div style="background-color:rgba(0,0,0,0.7);width:100%;height:100%;position:absolute;top:0;left:0;">';
    // strHtml += '</div>';
    alertFram.innerHTML = strHtml;
    document.body.appendChild(alertFram);
    document.body.appendChild(shield);
    $(".btn").on("mouseover", function() {
        $(this).css("background-color", "#daf3ef");
    });
    $(".btn").on("mouseout", function() {
        $(this).css("background-color", "#cfeae5");
    });
    $(".btn2").on("mouseover", function() {
        $(this).css("background-color", "#6cd1c4");
    });
    $(".btn2").on("mouseout", function() {
        $(this).css("background-color", "#63c9bd");
    });
    this.doOk = function() {
        alertFram.remove();
        shield.remove();
    };
    this.closeThis = function() {
        alertFram.remove();
        shield.remove();
    };
    alertFram.focus();
    document.body.onselectstart = function() {
        return false;
    };
};

//tab 切换封装
function tabsChose(num) {
    var tab = document.getElementsByClassName('tab')[0];
    var altab = tab.children[num];
    tab.children[num].className = 'col-md-12 localpage';
    for (var tl = 0; tl < tab.children.length; tl++) {
        tab.children[tl].onclick = function() {
            altab.className = 'col-md-12';
            altab = this;
            this.className = 'col-md-12 localpage';
        }
    }
};
$(function() {
    $(".luSe").on("mouseover", function() {
        $(this).css("background-color", "#6cd1c4");
    });
    $(".luSe").on("mouseout", function() {
        $(this).css("background-color", "#63c9bd");
    });
    $(".qianLu").on("mouseover", function() {
        $(this).css("background-color", "#daf3ef");
    });
    $(".qianLu").on("mouseout", function() {
        $(this).css("background-color", "#cfeae5");
    });
    $(".huiSe").on("mouseover", function() {
        $(this).css("background-color", "#eeeeee");
    });
    $(".huiSe").on("mouseout", function() {
        $(this).css("background-color", "#cdcdcd");
    });
    $(".hongSe").on("mouseover", function() {
        $(this).css("background-color", "#f1556b");
    });
    $(".hongSe").on("mouseout", function() {
        $(this).css("background-color", "#e45466");
    });
    $("#goTop").on("mouseover", function() {
        $(this).attr("class", "ongoTop")
    });
    $("#goTop").on("mouseout", function() {
        $(this).attr("class", "goTop")
    });
    $("#goTop").on("click", function() {
        $("html,body").animate({
            scrollTop : 0
        }, 500);
    });
});
