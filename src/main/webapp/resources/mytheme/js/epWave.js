function EPWave(c, b, a) {
    this.ELEMENT_HEIGHT = 20;
    this.CHARACTER_WIDTH = 8;
    this.DUMP_MAX_SIZE = 1048576;
    this.MAX_SIGNALS_FOR_DEFAULT_INDEX = 10;
    //change this
    this.VALUE_REGION_WIDTH = 20;
    this.CHARS_IN_VALUE_REGION = 2;
    
    this.HEX_BITS_IN_VALUE_REGION = 4 * this.CHARS_IN_VALUE_REGION;
    this.MIN_PIXELS_BETWEEN_HASHES = 10;
    this.MIN_PIXELS_BETWEEN_TENS = this.MIN_PIXELS_BETWEEN_HASHES * 10;
    this.waves = c;
    this.windowWidth = b;
    this.element = a;
    this.elementCursorTime = null;
    this.elementRemoveWave = null;
    this.elementUpWave = null;
    this.elementDownWave = null;
    this.elementStart = null;
    this.elementEnd = null;
    //this.radix=null;
    this.radix = 'hex';
    this.svgHeight = 0;
    this.textRegionWidth = 0;
    this.currentStart = 0;
    this.currentEnd = 0;
    this.cursorTime = null;
    this.timePerPixel = 0;
    this.waveIndex = [];
    this.selectedWaveIndex = null;
    this.loading = false;
    $("html").keydown({epWave: this}, function(e) {
        if (e.keyCode === 46) {
            e.data.epWave.removeSelectedWave();
        }
    }
    );
    this.isFirefox = navigator.userAgent.search("Firefox") > -1;
    this.isChrome = navigator.userAgent.search("Chrome") > -1;
    this.increaseValueRegion = function() 
    {
        
        for (f = 0; f < this.waveIndex.length; f++) 
        {
            //var j=8;
            //this.CHARS_IN_VALUE_REGION = j;
            //this.VALUE_REGION_WIDTH = j *10;
            //this.HEX_BITS_IN_VALUE_REGION=4*this.CHARS_IN_VALUE_REGION;			
            var g = this.waves.vars[this.waveIndex[f].id];
            var j = g.width;
            
            if (this.radix == "hex") 
            {
                
                this.CHARS_IN_VALUE_REGION = j / 4 > 5 ? j / 4 : 5;
                this.VALUE_REGION_WIDTH = this.CHARS_IN_VALUE_REGION * 10;
                this.HEX_BITS_IN_VALUE_REGION = 4 * this.CHARS_IN_VALUE_REGION;
            } 
            else if (this.radix == "binary") 
            
            {
                if (j > this.CHARS_IN_VALUE_REGION) {
                    this.CHARS_IN_VALUE_REGION = j;
                    this.VALUE_REGION_WIDTH = j * 10;
                    this.HEX_BITS_IN_VALUE_REGION = 4 * this.CHARS_IN_VALUE_REGION;
                }
            }
        
        
        
        }

    /**	for(var g in this.waves.vars){
			if(this.waves.vars.hasOwnProperty(g)){
				var j=g.data.width;
				if(j>5){
					this.CHARS_IN_VALUE_REGION = j;
					this.VALUE_REGION_WIDTH = j *10;
					this.HEX_BITS_IN_VALUE_REGION=4*this.CHARS_IN_VALUE_REGION;
					//CHARS_IN_VALUE_REGION = j;
					//VALUE_REGION_WIDTH = CHARS_IN_VALUE_REGION *10;
				
				}
			
			}
	
		}**/
    };
    this.setRadix = function(e) {
        if (e) {
            this.radix = e;
        } 
        else {
            this.radix = "hex";
        }
        if (this.radix === "hex") {
            $("#radixHex > i").css("visibility", "visible");
            $("#radixBinary > i").css("visibility", "hidden");
        } else {
            $("#radixBinary > i").css("visibility", "visible");
            $("#radixHex > i").css("visibility", "hidden");
        }
    };
    this.zoomIn = function() {
        var f = this.currentEnd - this.currentStart;
        var e = Math.floor(f / 4);
        if (e) {
            if (isNumber(this.cursorTime)) 
            {
                if (this.cursorTime - e < this.currentStart) {
                    this.createSvg(this.currentStart, this.currentEnd - 2 * e);
                } 
                else {
                    if (this.cursorTime + e > this.currentEnd) {
                        this.createSvg(this.currentStart + 2 * e, this.currentEnd);
                    } 
                    else {
                        this.createSvg(this.cursorTime - e, this.cursorTime + e);
                    }
                }
            } else {
                this.createSvg(this.currentStart + e, this.currentEnd - e);
            }
            this.setMarkerByTime(this.cursorTime);
        }
    };
    this.zoomOut = function() {
        var i = this.currentEnd - this.currentStart;
        var g = 2 * i;
        if (g >= this.waves.end - this.waves.start) {
            this.createSvg();
        } 
        else {
            var h = Math.floor(i / 2);
            var e = this.currentStart - h;
            if (e < this.waves.start) {
                e = this.waves.start;
            }
            var f = this.currentEnd + h;
            if (f > this.waves.end) 
            {
                f = this.waves.end;
                e = f - g;
            }
            this.createSvg(e, f);
        }
        this.setMarkerByTime(this.cursorTime);
    };
    this.zoomFull = function() {
        this.createSvg();
        this.setMarkerByTime(this.cursorTime);
    };
    this.moveLeft = function() {
        var g = this.currentEnd - this.currentStart;
        var f = Math.ceil(g / 4);
        var e = this.currentStart - f;
        if (e < this.waves.start) 
        {
            e = this.waves.start;
        }
        this.createSvg(e, e + g);
        this.setMarkerByTime(this.cursorTime);
    };
    this.moveLeftEnd = function() {
        var g = this.currentEnd - this.currentStart;
        //var f=Math.ceil(g/4);
        //var e=this.currentStart-f;
        //if(e<this.waves.start)
        //{
        var e = this.waves.start;
        //}
        this.createSvg(e, e + g);
        this.setMarkerByTime(this.cursorTime);
    };
    this.moveRight = function() 
    {
        var g = this.currentEnd - this.currentStart;
        var f = Math.ceil(g / 4);
        var e = this.currentEnd + f;
        if (e > this.waves.end) 
        {
            e = this.waves.end;
        }
        this.createSvg(e - g, e);
        this.setMarkerByTime(this.cursorTime);
    };
    this.moveRightEnd = function() 
    {
        var g = this.currentEnd - this.currentStart;
        //var f=Math.ceil(g/4);
        //var e=this.currentEnd+f;
        //if(e>this.waves.end)
        //{
        var e = this.waves.end;
        //}
        this.createSvg(e - g, e);
        this.setMarkerByTime(this.cursorTime);
    };
    this.centerAroundMarker = function() {
        var e = (this.currentEnd - this.currentStart) / 2;
        if (isNumber(this.cursorTime)) 
        {
            if (this.cursorTime - e < this.waves.start) {
                this.createSvg(this.waves.start, this.waves.start + 2 * e);
            } 
            else {
                if (this.cursorTime + e > this.waves.end) {
                    this.createSvg(this.waves.end - 2 * e, this.waves.end);
                } 
                else {
                    this.createSvg(this.cursorTime - e, this.cursorTime + e);
                }
            }
            this.setMarkerByTime(this.cursorTime);
        }
    };
    this.click = function(f, e) {
        if (f >= this.textRegionWidth) {
            if (e < this.ELEMENT_HEIGHT) {
                this.setMarkerByPosition(f);
                this.updateMarkerValues();
            } 
            else 
            {
                this.setMarkerByEdge(f, e - this.ELEMENT_HEIGHT);
            }
        } 
        else {
            if (e >= this.ELEMENT_HEIGHT && f <= (this.textRegionWidth - this.VALUE_REGION_WIDTH)) 
            {
                this.highlightWaveName(e - this.ELEMENT_HEIGHT);
            } 
            else 
            {
                this.setMarkerByTime(this.currentStart);
            }
        }
    };
    this.setMarkerByEdge = function(i, h) {
        var g = Math.floor(h / this.ELEMENT_HEIGHT), 
        j = this.waves.vars[this.waveIndex[g].id].data, 
        f = this.getTimeFromPosition(i), 
        l = null, 
        k = 0, 
        e = false;
        for (l in j) {
            if (j.hasOwnProperty(l)) {
                l = +l;
                if (l < f) {
                    k = l;
                } 
                else 
                {
                    if ((f - k) < (l - f)) {
                        e = true;
                        this.setMarkerByTime(k);
                    }
                    break;
                
                }
            
            }
        
        }
        if (!e) 
        {
            this.setMarkerByTime(l);
        }
        if (this.currentStart > this.cursorTime || this.cursorTime > this.currentEnd) 
        {
            this.centerAroundMarker();
        }
    };
    this.updateMarkerValues = function() {
        if (isNumber(this.cursorTime)) {
            var f;
            for (f = 0; f < this.waveIndex.length; f++) 
            {
                var g = this.waves.vars[this.waveIndex[f].id], 
                h = g.data, 
                k = null, 
                j = 0, 
                e = false;
                for (k in h) {
                    if (h.hasOwnProperty(k)) 
                    {
                        k = +k;
                        if (k <= this.cursorTime) 
                        {
                            j = k;
                        } 
                        else 
                        {
                            e = true;
                            this.updateMarkerValue(f, h[j], g.width);
                            break;
                        }
                    }
                }
                if (!e) {
                    this.updateMarkerValue(f, h[j], g.width);
                
                }
            
            }
        
        }
    
    };
    this.updateMarkerValue = function(g, j, h) {
        var f = this.radix == "hex" ? this.HEX_BITS_IN_VALUE_REGION : this.CHARS_IN_VALUE_REGION, 
        e = j;
        if (h > 1 && h <= f) 
        {
            j = this.getBusValue(j, h);
            e = j;
        } 
        else {
            if (h > f) {
                e = this.getBusValue(j, h);
                j = e.substring(e.length - this.CHARS_IN_VALUE_REGION);
            }
        }
        $("#value_" + g).contents().eq(1).wrap("<p />").parent().text(j).contents().unwrap();
        $("#value_" + g + " > title").text(e);
    };
    this.setMarkerByPosition = function(e) {
        var f = null;
        if (e >= this.textRegionWidth) 
        {
            f = this.getTimeFromPosition(e);
            this.setMarkerByTime(f);
        } 
        else 
        {
            lineElement.hide();
        }
        
        this.setCursorTime(f);
    };
    this.getTimeFromPosition = function(e) {
        return Math.round(((e - this.textRegionWidth) * this.timePerPixel).toFixed(2)) + this.currentStart;
    };
    this.setMarkerByTime = function(g) {
        var f = $(".verticalLine", this.element);
        if (isNumber(g) && this.currentStart <= g && g <= this.currentEnd) 
        {
            var e = Math.round(((g - this.currentStart) / this.timePerPixel + this.textRegionWidth).toFixed(2));
            $("line", f).attr({x1: e,x2: e});
            f.css("display", "block");
        } 
        else 
        {
            f.css("display", "none");
        }
        this.setCursorTime(g);
        this.updateMarkerValues();
    };
    this.setCursorTime = function(f) {
        this.cursorTime = f;
        if (this.elementCursorTime) 
        {
            var e = isNumber(f) ? false : true;
            f = e ? "-" : (this.numberWithCommas(f * this.waves.timescale.number) + this.waves.timescale.unit);
            this.elementCursorTime.html('<i class="cursorTimeArrow fa fa-location-arrow"></i> ' + f);
            if (e) 
            {
                this.elementCursorTime.addClass("disabled");
            } 
            else 
            {
                this.elementCursorTime.removeClass("disabled");
            }
        }
    };
    this.highlightWaveName = function(g) 
    {
        var f = Math.floor(g / this.ELEMENT_HEIGHT);
        var h = $(".waveName" + f);
        var e = h.css("fill");
        $(".waveName").css("fill", "black");
        if (e === "rgb(0, 0, 0)" || e === "#000000" || e === "black") 
        {
            this.fillWaveName(h, f);
        } 
        else {
            h.css("fill", "black");
            this.hideWaveNameFeatures();
        }
    };
    this.hideWaveNameFeatures = function() {
        this.selectedWaveIndex = null;
        this.elementRemoveWave.addClass("disabled");
        this.elementUpWave.addClass("disabled");
        this.elementDownWave.addClass("disabled");
        $(".fullSignalWell").hide();
    };
    this.fillWaveName = function(f, e) {
        this.selectedWaveIndex = e;
        this.elementRemoveWave.removeClass("disabled");
        if (e) 
        {
            this.elementUpWave.removeClass("disabled");
        } 
        else 
        {
            this.elementUpWave.addClass("disabled");
        }
        if (e < this.waveIndex.length - 1) 
        {
            this.elementDownWave.removeClass("disabled");
        } 
        else 
        {
            this.elementDownWave.addClass("disabled");
        }
        f.css("fill", "blue");
        $("#fullSignalName").text(this.waveIndex[e].signal.scope + "/" + this.waveIndex[e].signal.name);
        $(".fullSignalWell").show();
    };
    this.removeSelectedWave = function() {
        if (isNumber(this.selectedWaveIndex)) 
        {
            this.waveIndex.splice(this.selectedWaveIndex, 1);
            this.selectedWaveIndex = null;
            this.hideWaveNameFeatures();
            this.refreshSvg();
        }
    };
    this.upSelectedWave = function() {
        if (this.selectedWaveIndex) {
            var e = this.waveIndex[this.selectedWaveIndex];
            this.waveIndex[this.selectedWaveIndex] = this.waveIndex[this.selectedWaveIndex - 1];
            this.waveIndex[--this.selectedWaveIndex] = e;
            this.refreshSvg();
        }
    };
    this.downSelectedWave = function() {
        if (isNumber(this.selectedWaveIndex) && this.selectedWaveIndex < this.waveIndex.length - 1) {
            var e = this.waveIndex[this.selectedWaveIndex];
            this.waveIndex[this.selectedWaveIndex] = this.waveIndex[this.selectedWaveIndex + 1];
            this.waveIndex[++this.selectedWaveIndex] = e;
            this.refreshSvg();
        }
    };
    this.refreshSvg = function(e) {
        if (e) 
        {
            this.windowWidth = e;
        }
        this.createSvg(this.currentStart, this.currentEnd);
        this.setMarkerByTime(this.cursorTime);
    };
    this.populateStartEnd = function() {
        this.elementStart.val(this.numberWithCommas(this.waves.start * this.waves.timescale.number) + this.waves.timescale.unit);
        this.elementEnd.val(this.numberWithCommas(this.waves.end * this.waves.timescale.number) + this.waves.timescale.unit);
        this.increaseValueRegion();
    };
    this.populateSignalSelects = function(g, f, k, l) {
        var n = null, 
        m = {}, 
        j = {}, 
        o = null, 
        h;
        for (n in this.waves.vars) {
            if (this.waves.vars.hasOwnProperty(n)) {
                for (h = 0; h < this.waves.vars[n].signals.length; h++) 
                {
                    o = this.waves.vars[n].signals[h].scope;
                    m[o] = true;
                    if (!j[o]) 
                    {
                        j[o] = [];
                    }
                    j[o].push(this.waves.vars[n].signals[h].name);
                }
            }
        }
        for (n in j) 
        {
            if (j.hasOwnProperty(n)) 
            {
                j[n].sort();
            }
        }
        var e = [];
        for (o in m) 
        {
            if (m.hasOwnProperty(o)) 
            {
                e.push(o);
            }
        }
        e.sort();
        f.empty();
        this.populateScopeSelects(e, g);
        this.bindSignalSelects(j, g, f, k, l);
    };
    this.populateScopeSelects = function(e, f) {
        var p = [];
        f.empty();
        for (var l = 0; l < e.length; l++) 
        {
            var n = e[l];
            if (p.length) 
            {
                for (var h = p.length - 1; h >= 0; h--) 
                {
                    var m = n.search("^" + p[h] + "/");
                    if (m !== -1) 
                    {
                        var o = "";
                        for (var g = 0; g <= h; g++) 
                        {
                            o += ".";
                        }
                        n = o + n.slice(p[h].length + 1, n.length);
                        break;
                    }
                    p.pop();
                }
            }
            f.append($("<option></option").attr("value", e[l]).attr("title", e[l]).text(n));
            p.push(e[l]);
        }
    };
    this.bindSignalSelects = function(h, e, g, f, i) {
        e.unbind();
        e.change({names: h,elementNameSelect: g}, 
        function(m) {
            g.empty();
            var l = $("option:selected", this).val();
            for (var k = 0; k < m.data.names[l].length; k++) 
            {
                var j = m.data.names[l][k];
                g.append($("<option></option").attr("value", j).attr("title", j).text(j));
            }
        });
        
        g.unbind();
        g.dblclick({epWave: this,elementScopeSelect: e}, 
        function(k) {
            var j = [];
            j.push($("option:selected", this).val());
            k.data.epWave.appendWaves($("option:selected", k.data.elementScopeSelect).val(), j);
        });
        f.unbind();
        f.click({epWave: this,elementScopeSelect: e,elementNameSelect: g}, 
        function(l) {
            var j = [];
            var m = $("option:selected", l.data.elementNameSelect);
            for (var k = 0; k < m.length; k++) {
                j.push(m.eq(k).val());
            }
            l.data.epWave.appendWaves($("option:selected", l.data.elementScopeSelect).val(), j);
        }
        );
        i.unbind();
        i.click({
            epWave: this,elementScopeSelect: e,elementNameSelect: g}, 
        function(l) {
            var j = [];
            var m = $("option", l.data.elementNameSelect);
            for (var k = 0; k < m.length; k++) {
                j.push(m.eq(k).val());
            }
            l.data.epWave.appendWaves($("option:selected", l.data.elementScopeSelect).val(), j);
        });
    };
    this.appendWaves = function(h, e) {
        var k = {count: 0};
        var j = this.waveIndex.length;
        for (var g in this.waves.vars) {
            if (this.waves.vars.hasOwnProperty(g)) {
                for (var f = 0; f < this.waves.vars[g].signals.length && k.count !== e.length; f++) {
                    this.findSignalsForAppend(g, f, h, e, j, k);
                }
            }
        }
        this.refreshSvg();
    };
    this.findSignalsForAppend = function(h, g, j, f, n, m) {
        var l = this.waves.vars[h].signals[g];
        if (j === l.scope) {
            for (var e = 0; e < f.length && m.count !== f.length; e++) 
            {
                if (f[e] === l.name) {
                    this.waveIndex[n + e] = ({id: h,signal: l});
                    m.count++;
                    break;
                }
            }
        }
    };
    this.createDefaultIndex = function() {
        this.waveIndex = [];
        for (var e in this.waves.vars) {
            if (this.waves.vars.hasOwnProperty(e)) {
                this.waveIndex.push({id: e,signal: this.waves.vars[e].signals[0]});
            }
        }
    };
    /**this.reloadIndex=function()
	{
		if(this.waveIndex.length)
		{
			this.scrubCurrentIndex();	
		}	
		if(!this.waveIndex.length)
		{
				
			var h=0,
			f=null,	
			g=true;
			for(f in this.waves.vars)
			{
				if(this.waves.vars.hasOwnProperty(f))
				{	
					for(var e=0;e<this.waves.vars[f].signals.length;e++)
					{	
						this.waveIndex.push({id:f,signal:this.waves.vars[f].signals[e]});
						if(++h>this.MAX_SIGNALS_FOR_DEFAULT_INDEX)
						{
							g=false;
							break;	
						}		
					}	
					if(!g)
					{	
						break;	
					}	
				}	
			}		
			if(!g)
			{
				this.waveIndex=[];	
				this.selectedWaveIndex=null;	
			}
			else{	
				this.waveIndex.sort(sortBy("signal",true,function(i){return i.name.toLowerCase();
				}
				)
				);
			}
		}
	};**/
    /**this.scrubCurrentIndex=function(){
		var l=[];
		for(var f=0;f<this.waveIndex.length;f++)
		{
			var h=this.waveIndex[f],
			g=false;
			if(this.waves.vars[h.id]){
				for(var e=0;e<this.waves.vars[h.id].signals.length;e++)
				{
					var k=this.waves.vars[h.id].signals[e];
					if(k.name===h.signal.name&&k.scope===h.signal.scope)
					{
						g=true;
						break;
					}
				}
			}
			if(g)
			{
				l.push(h);
			}
		}
		this.waveIndex=l;
		this.selectedWaveIndex=null;
	};**/
    this.createSvg = function(f, h) 
    {
        this.increaseValueRegion();
        f = f ? f : this.waves.start;
        h = h ? h : this.waves.end;
        this.currentStart = f;
        this.currentEnd = h;
        var e = h - f, 
        j;
        if (!this.windowWidth) 
        {
            this.windowWidth = $('#waves').width();
        //this.windowWidth=$(window).width()-20;
        }
        var n = 1;
        var m = this.waveIndex.length;
        for (j = 0; j < this.waveIndex.length; j++) 
        {
            var g = this.waveIndex[j].signal.name.length;
            if (g > n) 
            {
                n = g;
            }
        
        }
        var l = n * this.CHARACTER_WIDTH;
        if (l > this.windowWidth / 2) 
        {
            l = this.windowWidth / 2;
        }
        this.textRegionWidth = l + this.VALUE_REGION_WIDTH;
        var o = this.windowWidth - this.textRegionWidth, 
        p = o / e;
        this.timePerPixel = e / o;
        this.svgHeight = this.ELEMENT_HEIGHT * (m + 1);
        var k = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="' + this.windowWidth + '" height="' + this.svgHeight + '"><defs><g id="b"><rect width="1" height="20" style="fill:rgb(0,0,0);stroke-width:0"></rect></g><g id="gray"><rect width="1" height="20" style="fill:DimGray;stroke-width:0"></rect></g><g id="zero"><line x1="0" y1="18" x2="1" y2="18" style="stroke:aqua; stroke-width:1"/></g><g id="one"><line x1="0" y1="2" x2="1" y2="2" style="stroke:limeGreen; stroke-width:1"/></g><g id="z"><line x1="0" y1="10" x2="1" y2="10" style="stroke:Peru; stroke-width:1"/></g><g id="x"><rect y="2" width="1" height="16" style="fill:#722232;stroke-width:0"/><line x1="0" y1="2" x2="1" y2="2" style="stroke:Crimson; stroke-width:1"/><line x1="0" y1="18" x2="1" y2="18" style="stroke:Crimson; stroke-width:1"/></g><g id="xEdge"><line x1="0" y1="18" x2="0" y2="2" style="stroke:Crimson; stroke-width:1"/></g><g id="risingEdge"><line x1="0" y1="18" x2="0" y2="2" style="stroke:limeGreen; stroke-width:1"/></g><g id="zeroToZ"><line x1="0" y1="18" x2="0" y2="10" style="stroke:limeGreen; stroke-width:1"/></g><g id="ZToOne"><line x1="0" y1="10" x2="0" y2="2" style="stroke:limeGreen; stroke-width:1"/></g><g id="fallingEdge"><line x1="0" y1="2" x2="0" y2="18" style="stroke:aqua; stroke-width:1"/></g><g id="oneToZ"><line x1="0" y1="2" x2="0" y2="10" style="stroke:aqua; stroke-width:1"/></g><g id="ZToZero"><line x1="0" y1="10" x2="0" y2="18" style="stroke:aqua; stroke-width:1"/></g><g id="bus"><line x1="0" y1="18" x2="1" y2="18" style="stroke:bisque; stroke-width:1"/><line x1="0" y1="2" x2="1" y2="2" style="stroke:bisque; stroke-width:1"/></g><g id="busChange"><line x1="0" y1="18" x2="3" y2="2" style="stroke:bisque; stroke-width:1"/><line x1="0" y1="2" x2="3" y2="18" style="stroke:bisque; stroke-width:1"/></g><g id="majorMark"><line x1="0" y1="18" x2="0" y2="0" style="stroke:yellow; stroke-width:1"/></g><g id="minorMark"><line x1="0" y1="18" x2="0" y2="12" style="stroke:yellow; stroke-width:1"/></g><g id="yellowLine"><line x1="0" y1="18" x2="1" y2="18" style="stroke:yellow; stroke-width:1"/></g><pattern id="oneZeroPattern" patternUnits="userSpaceOnUse" width="2" height="18"><line x1="1" y1="18" x2="1" y2="2" style="stroke:limeGreen; stroke-width:1"/><line x1="2" y1="18" x2="2" y2="2" style="stroke:aqua; stroke-width:1"/></pattern><pattern id="oneZeroPatternChrome" patternUnits="userSpaceOnUse" width="2" height="18"><line x1="0" y1="18" x2="0" y2="2" style="stroke:limeGreen; stroke-width:1"/><line x1="1" y1="18" x2="1" y2="2" style="stroke:aqua; stroke-width:1"/></pattern></defs>';
        k += this.createSvgRuler(l, p, o);
        k += this.createSvgWaves(l, o, p);
        k += '<g class="verticalLine" style="display:none;" transform="translate(0,0)">';
        k += '<line x1="100" y1="0" x2="100" y2="' + this.svgHeight + '"/></g>';
        k += "</svg>";
        a.html(k);
        if (isNumber(this.selectedWaveIndex)) 
        {
            this.fillWaveName($(".waveName" + this.selectedWaveIndex), 
            this.selectedWaveIndex);
        }
    };
    this.createSvgRuler = function(l, s, r) {
        var g = this.currentStart, 
        m = this.currentEnd, 
        i = this.getInterval(r, g, m), 
        h = i / 10, 
        n = 0, 
        j, 
        q, 
        f, 
        k, 
        p, 
        o = "";
        o += '<g transform="translate(0,0)">';
        o += '<use xlink:href="#gray" transform="scale(' + this.windowWidth + ',1)"/>';

        //o+='<rect class="waveName waveName'+"Signal"+'" width="1" height="'+this.ELEMENT_HEIGHT+'" style="fill:black;stroke-width:0" transform="scale('+144+',1)"></rect>';
        o += '<text class="name" x="' + l + '" y="15" text-anchor="end"><title>' + "Signal" + "</title>" + "Signal" + "</text>";
        o += '<text class="name" x="' + (this.VALUE_REGION_WIDTH - 4) + '" y="15" transform="translate(' + l + ')"  text-anchor="end"><title>' + "Value" + "</title>" + "Value" + "</text>";
        //o+='<text id="value_'+"value"+'" class="value" x="'+(this.VALUE_REGION_WIDTH-4)+'" y="15" transform="translate('+144+')" text-anchor="end"><title>Value</title> </text>';
        
        
        
        o += '<g transform="translate(' + this.textRegionWidth + ',0)">';
        o += '<use xlink:href="#yellowLine" transform="scale(' + r + ',1)"/>';
        if (g % i === 0) 
        {
            n = 0;
            o += '<use xlink:href="#majorMark" transform="translate(' + n + ')"/>';
            o += '<text class="time" x="1" y="12">' + this.numberWithCommas(g * this.waves.timescale.number) + "</text>";
            f = g;
        } 
        else 
        {
            if (g % h === 0) 
            {
                n = 0;
                o += '<use xlink:href="#minorMark" transform="translate(' + n + ')"/>';
                f = g;
            } 
            else 
            {
                k = h * Math.ceil(g / h);
                n = s * (k - g);
                j = Math.round(n.toFixed(2));
                if (k % i === 0) 
                {
                    o += '<use xlink:href="#majorMark" transform="translate(' + j + ')"/>';
                    o += '<text class="time" x="1" y="12" transform="translate(' + j + ')">' + this.numberWithCommas(k * this.waves.timescale.number) + "</text>";
                } 
                else 
                {
                    o += '<use xlink:href="#minorMark" transform="translate(' + j + ')"/>';
                }
                f = k;
            }
        }
        k = f + h;
        var e = s * h;
        do 
        {
            p = n + e;
            q = Math.round(p.toFixed(2));
            var l = k + h;
            if (k % i === 0) 
            {
                o += '<use xlink:href="#majorMark" transform="translate(' + q + ')"/>';
                if (l <= m) 
                {
                    o += '<text class="time" x="1" y="12" transform="translate(' + q + ')">' + this.numberWithCommas(k * this.waves.timescale.number) + "</text>";
                }
            } 
            else 
            {
                o += '<use xlink:href="#minorMark" transform="translate(' + q + ')"/>';
            }
            n = p;
            k = l;
        } 
        while (k <= m);
        return o + "</g></g>";
    };
    this.createSvgWaves = function(l, h, m) {
        var k = 1.2 * this.timePerPixel, 
        e = "";
        for (var g = 0; g < this.waveIndex.length; g++) 
        {
            var f = this.waveIndex[g].id;
            e += '<g transform="translate(0,' + (g + 1) * this.ELEMENT_HEIGHT + ')">';
            e += '<use xlink:href="#b" transform="scale(' + this.windowWidth + ',1)"/>';
            e += '<rect class="waveName waveName' + g + '" width="1" height="' + this.ELEMENT_HEIGHT + '" style="fill:black;stroke-width:0" transform="scale(' + l + ',1)"></rect>';
            e += '<text class="name" x="' + l + '" y="15" text-anchor="end"><title>' + this.waveIndex[g].signal.scope + "/" + this.waveIndex[g].signal.name + "</title>" + this.waveIndex[g].signal.name + "</text>";
            e += '<text id="value_' + g + '" class="value" x="' + (this.VALUE_REGION_WIDTH - 4) + '" y="15" transform="translate(' + l + ')" text-anchor="end"><title></title> </text>';
            e += '<g transform="translate(' + this.textRegionWidth + ',0)">';
            var j = this.waves.vars[f].data;
            if (this.waves.vars[f].width === 1) 
            {
                e += this.bitToSvg(j, k, h, m);
            } 
            else 
            {
                if (this.waves.vars[f].width !== 0) {
                    e += this.busToSvg(j, this.waves.vars[f].width, m);
                }
            }
            e += "</g></g>";
        }
        return e;
    };
    this.bitToSvg = function(h, k, m, n) {
        var i = {svg: "",start: this.currentStart,end: this.currentEnd,lastTime: this.currentStart,lastValue: "",lastLocation: 0,blockStart: 0,inBlock: false,time: 0
        };
        for (var f in h) 
        {
            if (h.hasOwnProperty(f)) 
            {
                i.time = +f;
                if (i.time < i.lastTime) 
                {
                    i.lastValue = h[i.time];
                } 
                else 
                {
                    if (i.time > i.end) 
                    {
                        break;
                    }
                    var l = h[i.time];
                    if (i.time !== i.lastTime) 
                    {
                        this.bitToSvgProcessOneTime(l, i, k, n);
                    
                    }
                    i.lastValue = l;
                }
            }
        }
        var g;
        if (i.inBlock) 
        {
            var j = Math.round(((i.lastTime - this.currentStart) / this.timePerPixel).toFixed(2));
            g = j - i.blockStart;
            i.svg += '<rect x="' + i.blockStart + '" y="1" width="' + g + '" height="18" fill="url(#oneZeroPattern' + (this.isChrome ? "Chrome" : "") + ')"></rect>';
        }
        if (i.lastTime !== i.end) 
        {
            var e = Math.round(i.lastLocation.toFixed(2));
            g = m - e;
            i.svg += '<use xlink:href="#' + this.getLastValueGroup(i.lastValue) + '" transform="translate(' + e + ") scale(" + g + ',1)"/>';
        }
        return i.svg;
    };
    this.bitToSvgProcessOneTime = function(k, i, l, m) {
        var g = i.time - i.lastTime, 
        e = i.lastTime === i.start, 
        j = g < l && !e && d(k);
        if (i.inBlock && j) 
        {
            i.lastTime = i.time;
        } 
        else 
        {
            if (j && d(i.lastValue)) {
                i.inBlock = true;
                i.blockStart = Math.round(i.lastLocation.toFixed(2));
                i.lastTime = i.time;
            } 
            else 
            {
                if (i.inBlock) {
                    i.inBlock = false;
                    i.lastLocation = (i.lastTime - this.currentStart) / this.timePerPixel;
                    var f = Math.round(i.lastLocation.toFixed(2)), h = f - i.blockStart;
                    if (h > 0) 
                    {
                        i.svg += '<rect x="' + i.blockStart + '" y="1" width="' + h + '" height="18" fill="url(#oneZeroPattern' + (this.isChrome ? "Chrome" : "") + ')"></rect>';
                    }
                }
                this.bitToSvgDraw(k, i, e, m, g);
            }
        }
    };
    /**	function d(e){
		return e===0||e===1;
	}**/
    this.bitToSvgDraw = function(n, k, e, o, i) {
        var f = o * i, 
        l = k.lastLocation + f, 
        m = Math.round(l.toFixed(2)), 
        h = Math.round(k.lastLocation.toFixed(2)), 
        j = m - h;
        if (j > 0 || e) 
        {
            if (j > 1) 
            {
                if (this.isChrome) 
                {
                    k.svg += '<use xlink:href="#' + this.getLastValueGroup(k.lastValue) + '" transform="translate(' + (h + 1) + ") scale(" + j + ',1)"/>';
                } 
                else 
                {
                    k.svg += '<use xlink:href="#' + this.getLastValueGroup(k.lastValue) + '" transform="translate(' + h + ") scale(" + j + ',1)"/>';
                }
            }
            var g = this.getEdgeGroup(k.lastValue, n);
            if (g) 
            {
                k.svg += '<use xlink:href="#' + g + '" transform="translate(' + m + ')"/>';
            }
            k.lastLocation = l;
            k.lastTime = k.time;
        }
    };
    this.busToSvg = function(k, h, o) 
    {
        var e = this.currentStart, 
        j = this.currentEnd, 
        n, 
        f, 
        i, 
        l = "", 
        m = {time: null,lastTime: e,lastLocation: 0,lastValue: ""};
        for (var g in k) 
        {
            if (k.hasOwnProperty(g)) 
            {
                m.time = +g;
                if (m.time < m.lastTime) 
                {
                    m.lastValue = k[m.time];
                } 
                else 
                {
                    if (m.time > j) 
                    {
                        break;
                    }
                    n = k[m.time];
                    if (m.time !== m.lastTime) 
                    {
                        l += this.busToSvgDraw(m, h, o);
                    }
                    m.lastValue = n;
                }
            }
        }
        if (m.lastTime !== j) 
        {
            f = Math.round(m.lastLocation.toFixed(2));
            f = f ? f + 1 : f;
            i = this.windowWidth - f - this.textRegionWidth;
            l += '<use xlink:href="#bus" transform="translate(' + f + ") scale(" + i + ',1)"/>';
            l += this.getBusText(m.lastValue, i, f, h);
        }
        return l;
    };
    this.busToSvgDraw = function(j, g, m) {
        var e = m * (j.time - j.lastTime), 
        k = j.lastLocation + e, 
        l = Math.round(k.toFixed(2)), 
        f = Math.round(j.lastLocation.toFixed(2)), 
        h = l - f - 2, 
        i = "";
        f = f ? f + 1 : f;
        if (h > 0) 
        {
            if (h > 1) 
            {
                if (this.isChrome) 
                {
                    i += '<use xlink:href="#bus" transform="translate(' + (f + 1) + ") scale(" + h + ',1)"/>';
                } 
                else 
                {
                    i += '<use xlink:href="#bus" transform="translate(' + f + ") scale(" + h + ',1)"/>';
                }
                i += this.getBusText(j.lastValue, h, f, g);
            }
            if (this.isChrome) 
            {
                i += '<use xlink:href="#busChange" transform="translate(' + (l - 1) + ')"/>';
            } 
            else 
            {
                i += '<use xlink:href="#busChange" transform="translate(' + (l - 2) + ')"/>';
            }
            j.lastLocation = k;
            j.lastTime = j.time;
        }
        return i;
    };
    
    this.getLastValueGroup = function(e) {
        switch (e) 
        {
            case 0:
                return "zero";
            case 1:
                return "one";
            case "Z":
                return "z";
            default:
                return "x";
        }
    };
    this.getEdgeGroup = function(e, f) 
    {
        if (f === "X") 
        {
            return "xEdge";
        }
        switch (e) {
            case 0:
                return this.getZeroEdgeGroup(f);
            case 1:
                return this.getOneEdgeGroup(f);
            case "Z":
                return this.getZEdgeGroup(f);
            case "X":
                return "xEdge";
            default:
                return "";
        }
    };
    this.getZeroEdgeGroup = function(e) {
        if (e === 1) 
        {
            return "risingEdge";
        } 
        else 
        {
            if (e === "Z") 
            {
                return "zeroToZ";
            }
        }
        return "";
    
    };
    this.getOneEdgeGroup = function(e) {
        if (e === 0) 
        {
            return "fallingEdge";
        
        } 
        else {
            if (e === "Z") 
            {
                return "oneToZ";
            }
        }
        return "";
    };
    this.getZEdgeGroup = function(e) 
    {
        if (e === 0) 
        {
            return "ZToZero";
        } 
        else 
        {
            if (e === 1) 
            {
                return "ZToOne";
            }
        }
        return "";
    };
    this.getInterval = function(e, j, g) {
        var i = g - j, 
        h = this.numberWithCommas(g * this.waves.timescale.number).length * this.CHARACTER_WIDTH, 
        f = i / (e / Math.max(h, this.MIN_PIXELS_BETWEEN_TENS)), 
        k = 1;
        while (f > 50) 
        {
            k *= 10;
            f /= 10;
        }
        if (f <= 10) 
        {
            return 10 * k;
        } 
        else 
        {
            if (f <= 20) 
            {
                return 20 * k;
            } 
            else 
            {
                if (f <= 50) 
                {
                    return 50 * k;
                
                }
            }
        }
    };
    this.getBusText = function(h, i, e, g) 
    {
        var f = this.trimText(this.getBusValue(h, g), i);
        if (f) 
        {
            return '<text class="busData" x="0" y="14" transform="translate(' + e + ')">' + f + "</text>";
        }
        return "";
    };
    this.getBusValue = function(f, e) 
    {
        if (this.radix === "hex") 
        {
            return vcd2hex(f, e);
        } 
        else 
        {
            return vcd2bin(f, e);
        }
    
    };
    this.trimText = function(f, g) {
        var e = g > 0 ? Math.floor(g / this.CHARACTER_WIDTH) : 0;
        if (f.length > e) 
        {
            if (e === 0) 
            {
                return "";
            
            } 
            else 
            {
                if (e === 1) 
                
                {
                    return "*";
                } 
                else 
                {
                    return "*" + f.substring(f.length - e + 1);
                }
            }
        }
        return f;
    };
    this.numberWithCommas = function(e) {
        return e.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    };
    this.numberWithoutCommas = function(e) {
        return e.toString().replace(/,/g, "");
    };

/**this.showLoad=function(){
		this.loading=true;
		var e=$("#loadButton");
	
		e.addClass("disabled");
	
		$(".fa-refresh",e).addClass("fa-spin");
		
	};**/
/***this.showLoadDone=function()
	{
		this.loading=false;
		var e=$("#loadButton");
	
		e.removeClass("disabled");
	
		$(".fa-refresh",e).removeClass("fa-spin");
		
	};**/
}
function isNumber(a) {
    return !isNaN(parseFloat(a)) && isFinite(a);
}
function vcd2hex(b, e) {
    var a = vcd2hexChunks(b);
    a = vcd2hexRemainder(b, a);
    if (e > b.length) {
        var g = null;
        if (b[0] === "z") 
        {
            g = "Z";
        } 
        else 
        {
            if (b[0] === "x") 
            {
                g = "X";
            }
        }
        if (g) 
        {
            var f = Math.ceil(e / 4), 
            d = Math.ceil(b.length / 4), 
            c;
            for (c = 0; c < (f - d); c++) 
            {
                a = appendToResult(a, g);
            }
        }
    }
    return a.split("").reverse().join("");
}
function vcd2hexChunks(b) {
    var e, 
    c, 
    a = "";
    for (var d = b.length - 4; d >= 0; d -= 4) 
    {
        c = b.substr(d, 4);
        if (c === "xxxx") 
        {
            e = "X";
        } 
        else 
        {
            if (c === "zzzz") 
            {
                e = "Z";
            } 
            else 
            {
                if (c.indexOf("x") >= 0) 
                {
                    e = "x";
                } 
                else 
                {
                    if (c.indexOf("z") >= 0) 
                    {
                        e = "z";
                    } 
                    else 
                    {
                        e = parseInt(c, 2).toString(16);
                    }
                }
            }
        }
        a = appendToResult(a, e);
    }
    return a;

}
function vcd2hexRemainder(b, a) {
    var d, 
    c;
    if (b.length % 4) 
    {
        c = b.substr(0, b.length % 4);
        if (c === "x" || c === "xx" || c === "xxx") 
        {
            d = "X";
        } 
        else 
        {
            if (c === "z" || c === "zz" || c === "zzz") {
                d = "Z";
            
            } 
            else 
            {
                if (c.indexOf("x") >= 0) 
                {
                    d = "x";
                
                } 
                else 
                {
                    if (c.indexOf("z") >= 0) 
                    {
                        d = "z";
                    } 
                    else 
                    {
                        d = parseInt(c, 2);
                    }
                }
            }
        }
        a = appendToResult(a, d);
    
    }
    
    return a;

}
function vcd2bin(a, d) 
{
    if (d > a.length) 
    {
        var f = null;
        if (a[0] === "z") {
            f = "z";
        } 
        else 
        {
            if (a[0] === "x") 
            {
                f = "x";
            }
        }
        if (f) 
        {
            var b = "", 
            c, 
            e = d - a.length;
            for (c = 0; c < e; c++) 
            {
                b += f;
            }
            return b + a;
        }
    }
    
    return a;
}
function appendToResult(a, b) 
{
    if (a.length !== 4 && ((a.length + 1) % 5)) 
    {
        return a += b;
    }
    var c = a += "_" + b;
    return c;
}
