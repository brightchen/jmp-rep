/*
	written by Kae Verens
	kae@verens.com
	please keep this comment intact
	you may use this source code freely
*/
function getOffset(el,s) {
  var n=parseInt(el['offset'+s]),p=el.offsetParent;
  if(p)n+=getOffset(p,s);
  return n;
}
function imagefader_init(){
	window.imagefader_data=[];
	var numfound=0;
	var els=document.getElementsByTagName('img');
	var c=/(^| )imagefader($| )/;
	for(var i=0;i<els.length;++i){
		if(c.test(els[i].className)){
			var images=els[i].className.replace(/imagefader/,'').split(' ');
			var id=els[i].id?els[i].id:'imagefader_img_'+i;
			els[i].id=id;
			var data={images:[els[i].src],current:0,id:id};
			for(var j=0;j<images.length;++j)if(images[j]!='')data.images.push(images[j]);
			imagefader_data.push(data);
		}
	}
	if(imagefader_data.length)setTimeout('imagefader_rotate()',1000);
}
function imagefader_rotate(){
	for(var i=0;i<imagefader_data.length;++i){
		imagefader_data[i].current++;
		var d=imagefader_data[i];
		var l=d.images.length,el=document.getElementById(d.id);
		var img=document.createElement('img');
		img.src=d.images[d.current%l];
		img.style.opacity=0;
		img.style.filter='alpha(opacity=0)';
		img.style.position='absolute';
		img.style.left=getOffset(el,'Left')+'px';
		img.style.top=getOffset(el,'Top')+'px';
		img.style.zIndex=2;
		img.id='imagefader'+i;
		document.body.appendChild(img);
	}
	setTimeout('imagefader_fade(.1)',100);
}
function imagefader_fade(o){
	for(var i=0;i<imagefader_data.length;++i){
		var d=imagefader_data[i],overlay=document.getElementById('imagefader'+i);
		overlay.style.opacity=o;
		overlay.style.filter='alpha(opacity='+(o*100)+')';
	}
	if(o>=1)setTimeout('imagefader_set()',100);
	else setTimeout('imagefader_fade('+(o+.02)+')',100);
}
function imagefader_set(){
	for(var i=0;i<imagefader_data.length;++i){
		var d=imagefader_data[i];
		var el=document.getElementById(d.id);
		el.src=d.images[d.current%d.images.length];
		document.body.removeChild(document.getElementById('imagefader'+i));
	}
	setTimeout('imagefader_rotate()',1000);
}
