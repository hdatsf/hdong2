/*
 * Bootstrap 3.3.6 IconPicker - jQuery plugin for Icon selection
 *
 * Copyright (c) 20013 A. K. M. Rezaul Karim<titosust@gmail.com>
 * Modifications (c) 20015 Paden Clayton<fasttracksites.com>
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 *
 * Project home:
 *   https://github.com/titosust/Bootstrap-icon-picker
 *
 * Version:  1.0.1
 *
 */

(function($) {

    $.fn.iconPicker = function( options, imgBasePath ) {
        
        var mouseOver=false;
        var $popup=null;
        var basePath = imgBasePath?imgBasePath:"";
        var icons=new Array("userB_1.jpg","userB_2.jpg","userB_3.jpg","userB_4.jpg","userB_5.jpg","userB_6.jpg","userB_7.jpg","userB_8.jpg","userB_9.jpg","userB_10.jpg","userB_11.jpg","userB_12.jpg","userB_13.jpg","userB_14.jpg","userB_15.jpg","userB_16.jpg","userB_17.jpg","userB_18.jpg","userB_19.jpg","userB_20.jpg","userB_21.jpg","userB_22.jpg","userB_23.jpg","userB_24.jpg","userG_1.jpg","userG_2.jpg","userG_3.jpg","userG_4.jpg","userG_5.jpg","userG_6.jpg","userG_7.jpg","userG_8.jpg","userG_9.jpg","userG_10.jpg","userG_11.jpg","userG_12.jpg","userG_13.jpg","userG_14.jpg","userG_15.jpg","userG_16.jpg","userG_17.jpg","userG_18.jpg","userG_19.jpg","userG_20.jpg","userG_21.jpg","userG_22.jpg","userG_23.jpg","userG_24.jpg","userR_1.jpg","userR_2.jpg","userR_3.jpg","userR_4.jpg","userR_5.jpg","userR_6.jpg","userR_7.jpg","userR_8.jpg","userR_9.jpg","userR_10.jpg","userR_11.jpg","userR_12.jpg","userR_13.jpg","userR_14.jpg","userR_15.jpg","userR_16.jpg","userR_17.jpg","userR_18.jpg","userR_19.jpg","userR_20.jpg","userR_21.jpg","userR_22.jpg","userR_23.jpg","userR_24.jpg","userY_1.jpg","userY_2.jpg","userY_3.jpg","userY_4.jpg","userY_5.jpg","userY_6.jpg","userY_7.jpg","userY_8.jpg","userY_9.jpg","userY_10.jpg","userY_11.jpg","userY_12.jpg","userY_13.jpg","userY_14.jpg","userY_15.jpg","userY_16.jpg","userY_17.jpg","userY_18.jpg","userY_19.jpg","userY_20.jpg","userY_21.jpg","userY_22.jpg","userY_23.jpg","userY_24.jpg");
        var settings = $.extend({
        	
        }, options);
        return this.each( function() {
        	element=this;
            if(!settings.buttonOnly && $(this).data("iconPicker")==undefined ){
            	$this=$(this).addClass("form-control");
            	$wraper=$("<div/>",{class:"input-group"});
            	$this.wrap($wraper);

            	$button=$("<span class=\"input-group-addon pointer\">选择</span>");
            	$this.after($button);
            	(function(ele){
	            	$button.click(function(){
			       		createUI(ele);
			       		showList(ele,icons);
	            	});
	            })($this);

            	$(this).data("iconPicker",{attached:true});
            }
        
	        function createUI($element){
	        	$popup=$('<div/>',{
	        		css: {
		        		'top':$element.offset().top+$element.outerHeight()+6,
		        		'left':$element.offset().left
		        	},
		        	class:'icon-popup'
	        	})

	        	$popup.html('<div class="ip-control"> \
						          <ul> \
						            <li><a href="javascript:;" class="btn" data-dir="-1"><span class="glyphicon  glyphicon-fast-backward"></span></a></li> \
						            <li><input type="text" class="ip-search glyphicon  glyphicon-search" placeholder="Search" /></li> \
						            <li><a href="javascript:;"  class="btn" data-dir="1"><span class="glyphicon  glyphicon-fast-forward"></span></a></li> \
						          </ul> \
						      </div> \
						     <div class="icon-list"> </div> \
					         ').appendTo("body");
	        	
	        	
	        	$popup.addClass('dropdown-menu').show();
				$popup.mouseenter(function() {  mouseOver=true;  }).mouseleave(function() { mouseOver=false;  });

	        	var lastVal="", start_index=0,per_page=30,end_index=start_index+per_page;
	        	$(".ip-control .btn",$popup).click(function(e){
	                e.stopPropagation();
	                var dir=$(this).attr("data-dir");
	                start_index=start_index+per_page*dir;
	                start_index=start_index<0?0:start_index;
	                if(start_index+per_page<=210){
	                  $.each($(".icon-list>ul li"),function(i){
	                      if(i>=start_index && i<start_index+per_page){
	                         $(this).show();
	                      }else{
	                        $(this).hide();
	                      }
	                  });
	                }else{
	                  start_index=180;
	                }
	            });
	        	
	        	$('.ip-control .ip-search',$popup).on("keyup",function(e){
	                if(lastVal!=$(this).val()){
	                    lastVal=$(this).val();
	                    if(lastVal==""){
	                    	showList(icons);
	                    }else{
	                    	showList($element, $(icons)
							        .map(function(i,v){ 
								            if(v.toLowerCase().indexOf(lastVal.toLowerCase())!=-1){return v} 
								        }).get());
						}
	                    
	                }
	            });  
	        	$(document).mouseup(function (e){
				    if (!$popup.is(e.target) && $popup.has(e.target).length === 0) {
				        removeInstance();
				    }
				});

	        }
	        function removeInstance(){
	        	$(".icon-popup").remove();
	        }
	        function showList($element,arrLis){
	        	$ul=$("<ul>");
				
	        	for (var i in arrLis) {
	        		$ul.append("<li><img class=\"pointer\" title=\""+arrLis[i]+"\" src=\""+basePath+arrLis[i]+"\"/></li>");
	        	};
	        	$(".icon-list",$popup).html($ul);
	        	$(".icon-list li img",$popup).click(function(e){
	        		e.preventDefault();
	        		var title=$(this).attr("title");
	        		$element.val(title);
	        		removeInstance();
	        	});
	        }

        });
    }

}(jQuery));
