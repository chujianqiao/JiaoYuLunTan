myJs={
		//页面所需FUN执行
		render:function(){
			//里面是首页初始化时需要的函数
			this.poster_animate();
			this.hot_swiper_init();
			this.tab();
			this.languageSelect('#langSelect');
			this.languageSelect('#langSelect_mobile');
			this.navTab();
			this.funcSwiper();
			this.defaultImg1('.forum-topLists img');
			this.mobile_swiper_init1();
			this.mobile_swiper_init2();
			this.mobile_swiper_init3();
			this.mobile_swiper_init4();
		},
		//开屏动画
		poster_animate:function(){
			setTimeout(function(){
				$('.poster-container').animate({'height':0,"opacity":0},1000);
			},3000);
		},
		//swiper相关
		swiper_init:function(id,pagination,nextEl,prevEl,num,autoplay,space){
			var swiper=new Swiper(''+id,{
				slidesPerView:num||1,
				init:false,
				autoplay:autoplay||false,
				pagination: {
			      el: pagination,
			      clickable:true
			    },
			    navigation:{
			      nextEl: nextEl,
			      prevEl: prevEl,
			    }
			});
			return swiper;
		},
		//热点swiper
		hot_swiper_init:function(){
			var hot_swiper=myJs.swiper_init('.hot-swiper-container',null,'.hot-swiper-button-next','.hot-swiper-button-prev',1,true);
			hot_swiper.init();
			if(hot_swiper.slides.length<=1){hot_swiper.params.noSwiping=true;$('.hot-swiper-button-prev,.hot-swiper-pagination,.hot-swiper-button-next').addClass('hide')};
			var htmlC=myJs.getTitle($(hot_swiper.slides).first());
			$('.hot-swiper-container .hydt-imgTitle').html(htmlC);
			var htmlC=myJs.getTitle($(hot_swiper.slides).first());
			$('.hot-swiper-container .hydt-imgTitle').html(htmlC);
			hot_swiper.on('slideChangeTransitionEnd',function(){
				$slides=$('.hot-swiper-container .swiper-slide');
				var activeIndex=hot_swiper.activeIndex;
				var $item=$slides.eq(activeIndex);
				var htmlC=myJs.getTitle($item);
				$('.hot-swiper-container .hydt-imgTitle').html(htmlC);
				var htmlC=myJs.getTitle($item);
				$('.hot-swiper-container .hydt-imgTitle').html(htmlC);
			});
		},
		//首页论坛嘉宾和论坛花絮切换
		tab:function(){
			$('#tabs').children().mouseenter(function(){
				var $dom=$('#contContainer').children(),link='',index=$(this).index();
				$(this).addClass('hover').siblings().removeClass('hover');
				$dom.addClass('hide').eq(index).removeClass('hide');
				link=$(this).attr('data-link');
				$('#more').attr('href',link)
			})
		},
		//语言选择
		languageSelect:function(id){
			$(id).click(function(){
				var src=$(this).attr('src');
				if(src.includes('show')){
					$(this).attr('src','images/hide.png');
					$(this).siblings('.language').css('height','auto')
				}else{
					$(this).attr('src','images/show.png');
					$(this).siblings('.language').css('height','.3rem')
				}
			});
		},
		//导航切换
		navTab:function(){
			$('.nav_pc').children().mouseenter(function(e){
				var $target=$(this).children('.childNav');
				if($target.length>0){
					$(this).find('.item').addClass('hover');
					$target.removeClass('hide');
				}
			}).mouseleave(function(){
				var $target=$(this).children('.childNav');
				if($target.length>0){
					$target.siblings('.item').removeClass('hover');
					$target.addClass('hide');
				}
			});
		},
		//功能专区swiper
		funcSwiper:function(){
			var space=($('#funcSwiper').width()-$('.func-item').first().width()*7)/6;
			var mySwiper=this.swiper_init('#funcSwiper',null,'.func-container .swiper-button-next','.func-container .swiper-button-prev',7,false);
			mySwiper.init();
		},
		//论坛活动左侧导航树展开效果
		navTree:function(){
			$('.navTree-item').each(function(){
				var $toggleIcon=$(this).find('.toggleBtn');
				var $lists=$(this).find('.lavel1-list');
				var $title=$(this).find('.title');
				if($lists.find('li').length>0){
					if(!$toggleIcon.hasClass('up')){$toggleIcon.addClass('down');}
					//$toggleIcon.siblings('a').attr('href','javascript:void(0)').css('cursor','default')
					$toggleIcon.click(function(index){
						$('.navTree').find('.title').removeClass('hover');
						$title.addClass('hover');
						if($(this).hasClass('down')){
							$(this).removeClass('down').addClass('up');
							$lists.stop().slideDown();
						}else{
							$(this).removeClass('up').addClass('down');
							$lists.stop().slideUp();
						}
					});
				}
			});
			$('.lavel1-item').each(function(){
				var lavelNum=$(this).find('.lavel2-item').length;
				var $carset=$(this).find('.carset1');
				var $this=$(this);
				if(lavelNum>0){
					if(!$carset.hasClass('up')){$carset.addClass('down');}
					//$carset.siblings('a').attr('href','javascript:void(0)').css('cursor','default');
					$carset.click(function(){
						if($(this).hasClass('down')){
							$(this).removeClass('down').addClass('up');
							$this.find('.lavel2').slideDown();
						}else{
							$(this).removeClass('up').addClass('down');
							$this.find('.lavel2').slideUp();
						}
					});
				}
			});
		},
		//默认图片设置
		defaultImg1:function(id){
			$(id).on('error',function(){
				this.onerror=null;
				this.src="images/alt1.jpg";
			});
		},
		defaultImg2:function(id){
			$(id).on('error',function(){
				this.onerror=null;
				this.src="images/alt2.jpg";
			});
		},
		mobile_swiper_init1:function(){
			var swiperWidth=0,wiperWidth=0;
			var swiper=new Swiper('#mobileSwiper1',{
				slidesPerView:4,
				on:{
					tap:function(e){
							var $target=$(e.target);
							var $item=$(swiper.clickedSlide);
							var $childLavel=$item.find('.lavel2');
							var left=swiper.getTranslate();
							$childLavel.css('left',-left);
							if($childLavel.length>0){
								if($item.find('.carset').hasClass('down')){
									$item.siblings().find('.carset').removeClass('up').addClass('down');
									$item.siblings().find('.lavel2').addClass('hide');
									$item.find('.carset').removeClass('down').addClass('up');
									$childLavel.removeClass('hide');
								}else{
									if($target.attr('href')){
										return;
									}else{
										$item.find('.carset').removeClass('up').addClass('down');
										$childLavel.addClass('hide');
									}	
								}
							}
					},
				 	sliderMove:function(){
				 		$('.lavel2').addClass('hide');
				 	},
				 	progress:function(progress){
				 		if(progress<1){$('#mobileSwiper1').siblings('.cover').css('z-index','3');}else{$('#mobileSwiper1').siblings('.cover').css('z-index','1');}
				 	}
				}
			});
		},
		getTitle:function(item){
				var hrefValue=$(item).find('a').attr('href');
				var title=$(item).find('a').attr('title');
				var htmlC="<a href='"+hrefValue+"' title='"+title+"' target='_blank'>"+title+"</a>";
				return htmlC;
			},
		mobile_swiper_init2:function(){
			var swiper=this.swiper_init('#mobileSwiper2','#mobileSwiper2 .swiper-pagination',null,null,1,true);
			var length=$('.mobile_swiper2 img').length;
			if(length>1){swiper.init();}
			swiper.on('tap',function(){
				var $item=$(swiper.clickedSlide);
				var href=$item.find('a').attr('href');
				window.open(href);
			});
		},
		//会议动态swiper
		mobile_swiper_init3:function(){
			var $slides=$('#mobileSwiper3 .swiper-slide');
			var swiper=this.swiper_init('#mobileSwiper3','#mobileSwiper3 .swiper-pagination','#mobileSwiper3 .swiper-button-next','#mobileSwiper3 .swiper-button-prev');
			swiper.on('init',function(){
				var hrefValue=$slides.first().find('a').attr('href');
				var htmlC=myJs.getTitle($slides.first());
				$('.mobile-hydt-title1').html(htmlC);
			});
			swiper.on('slideChangeTransitionEnd',function(){
				var activeIndex=swiper.activeIndex;
				var $item=$slides.eq(activeIndex);
				var htmlC=myJs.getTitle($item);
				$('.mobile-hydt-title1').html(htmlC);
			});
			if($slides.length>1){
				swiper.init();
			}
		},
		formTab:function(){
			var $tab=$('#formTab');
			var $forms=$('#formList').find('.form');
			$tab.find('span').click(function(){
				var index=$(this).index();
				$tab.find('span').removeClass('hover');
				$(this).addClass('hover');
				$forms.addClass('hide').eq(index).removeClass('hide');
			});
		},
		mobile_form:function(){
			var $tab=$('#formTab');
			var $forms=$('#formList').find('.mobile-form');
			$tab.find('span').tap(function(){
				var index=$(this).index();
				$tab.find('span').removeClass('hover');
				$(this).addClass('hover');
				$forms.addClass('hide').eq(index).removeClass('hide');
			});
		},
		//功能专区swiper
		mobile_swiper_init4:function(){
			var swiper=this.swiper_init('#mobileSwiper4','#mobileSwiper4 .swiper-pagination');
			var slides=$('#mobileSwiper4 .swiper-slide');
			swiper.params.spaceBetween=80
			if(slides.length>1){
				swiper.init()
			}
		}
	}
	