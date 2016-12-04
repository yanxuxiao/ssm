// JavaScript Document
var winW = $(window).width();
var winH = $(window).height();
var speed1 = 2000;
var speed2 = 500;
var speed3 = 3000;
var ml = "50%";
var mr = "50%";
var scale = 0;
$(function(){
	$("#err404 .tip").animate({left:ml},speed1,function(){
		$("#err404 .tip").addClass("stop");
	});
	$("#err404 .robot").animate({right:mr},speed1/*,function(){
		setTimeout(function(){$("#err404 .robot").addClass("stop");},1000);
	}*/);
	
	//setInterval(scalePop,speed3);
	setTimeout(function(){
		$(".robot2Wrap").show();
		$(".robot2Wrap").animate({top:0},500,function(){
			
			$(".robot2Wrap").addClass("stop");
			});
		},1000);
	//setInterval(translate,speed3/*,function(){
		$("#err500 .img2").removeClass("stop");
		$("#err500 .img3").removeClass("stop");
	//}*/);
	
});

/*
function scalePop()
{
	scale+=0.1;
	$("#err500 .img1").css("-webkit-transform","scale(1)");
}*/