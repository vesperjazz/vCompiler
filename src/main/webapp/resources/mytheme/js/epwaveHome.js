var loading=false,epWave;
$(document).ready(function(){
	var width = $('#waves').width();
	//alert(width);
		//var b=$(window).width()-20;
		var b= $('#waves').width();
		epWave=new EPWave(dump,b,$("#waves"));
		epWave.elementCursorTime=$(".cursorTime");
		epWave.elementRemoveWave=$(".removeWave");
		epWave.elementUpWave=$(".upWave");
		epWave.elementDownWave=$(".downWave");
		epWave.elementStart=$("#start");
		epWave.elementEnd=$("#end");
		epWave.waveIndex=EPWAVE_WAVE_INDEX;
		//epWave.reloadIndex();
		epWave.setRadix(EPWAVE_RADIX);
		epWave.createSvg(EPWAVE_START,EPWAVE_END);
		epWave.setMarkerByTime(EPWAVE_CURSOR_TIME);
		
		if(!EPWAVE_DEFAULT){
			epWave.populateStartEnd();
			}
		epWave.populateSignalSelects($("#scopeSelect"),$("#nameSelect"),$("#appendSelected"),$("#appendAll"));
		//epWave.increaseValueRegion();
		$(".zoomIn").click({epWave:epWave},function(d){
			d.data.epWave.zoomIn();
			});
		$(".zoomOut").click({epWave:epWave},
				function(d){
			d.data.epWave.zoomOut();
			});
		$(".zoomFull").click({epWave:epWave},function(d){
			d.data.epWave.zoomFull();
			});
		$(".moveLeft").click({epWave:epWave},function(d){
			d.data.epWave.moveLeft();
			});
		$(".moveLeftEnd").click({epWave:epWave},function(d){
			d.data.epWave.moveLeftEnd();
			});
		$(".moveRight").click({epWave:epWave},function(d){
			d.data.epWave.moveRight();
			});
		$(".moveRightEnd").click({epWave:epWave},function(d){
			d.data.epWave.moveRightEnd();
			});		
		$("#waves").click({epWave:epWave},function(e){
			var d=$(this).position();
			e.data.epWave.click(e.clientX-d.left,e.clientY+$(document).scrollTop()-d.top);
		});
		epWave.elementCursorTime.click({epWave:epWave},function(d){
			d.data.epWave.centerAroundMarker();
			});
		var a=_.debounce(function(d){
			//d.data.epWave.refreshSvg($(window).width()-20);
			d.data.epWave.refreshSvg($('#waves').width()-20);
			},300);
		$(window).resize({epWave:epWave},a);
		epWave.elementRemoveWave.click({epWave:epWave},function(d){
			d.data.epWave.removeSelectedWave();
			});
		epWave.elementUpWave.click({epWave:epWave},function(d){
			d.data.epWave.upSelectedWave();
			});
		epWave.elementDownWave.click({epWave:epWave},function(d){

			d.data.epWave.downSelectedWave();
			});
		$(".signalSelect").click(function(){$("#signalSelectModal").modal();});
		$("#radixHex").click({
			epWave:epWave},function(d){
				if(d.data.epWave.radix!=="hex"){
					d.data.epWave.setRadix("hex");
					d.data.epWave.refreshSvg();
					}
				});
		$("#radixBinary").click({epWave:epWave},function(d){
			if(d.data.epWave.radix!=="binary"){
				

				d.data.epWave.setRadix("binary");
				d.data.epWave.refreshSvg();
				}
			});
		});
