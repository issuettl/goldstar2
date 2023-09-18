
vcui.define('common/footer', ['jquery', 'vcui', 'ui/dropdown' ], function ($, core) {
    "use strict";

    var Footer = core.ui('Footer', {
        bindjQuery: true,
        defaults: {
        },
        prevWinWidth:null,

        initialize: function initialize(el, options) {

            if (!!lgkorUI.CONTEXT_AREA == false){
                lgkorUI.CONTEXT_AREA = $(document);
            }

            var self = this;
            
            if (self.supr(el, options) === false) {
                return;
            };


            self.$mobileLinks = null;
            // self.$pcLinkes = self.$el.find('.cont-area .link-wrap');
            self.$pcLinkes = self.$el.find('.cont-area .pc-dropdown-wrap');

            self.$el.find('.menu-opener').on('click', function(e){
                self.$pcLinkes.toggleClass('open');

                var openerName = self.$pcLinkes.hasClass('open') ? "메뉴 접기" : "메뉴 전체보기";
                $(this).find('span').text(openerName);
            });
            
            $(window).on('resizeend', function(e){
                self._resize();
            });
            self._resize();
            //$(window).trigger('addResizeCallback', self._resize.bind(self));

            if(!vcui.detect.isMobileDevice){
                self.$el.on('click', 'a', function(e){
                    var exist = $(this).attr('href').indexOf("tel");
                    if(exist > -1){
                        e.preventDefault();
                    }
                });
            }else {
                // S : BTOCSITE-19222
                if(isApp()) {
                    self.$el.on('click', '.sns-link .sns6, .bottom-link a', function(e){
                        var target = this.getAttribute('target');
                        if(target == '_blank') {
                            e.preventDefault();
                            var appUrl = $(this).attr('href');
                            if (!(appUrl.match('http'))) {
                                appUrl = 'https://'+window.LGEAPPHostName+appUrl;
                            } 
                            if(vcui.detect.isIOS){
                                var jsonString = JSON.stringify({'url': appUrl, 'command':'sendOutLink'});
                                webkit.messageHandlers.callbackHandler.postMessage(jsonString);
                            } else {
                                android.openLinkOut(appUrl);
                            }
                        }
                    });
                }
                // E : BTOCSITE-19222
            }

            // LGECOMVIO-192 베스트샵 마이페이지_인테리어상담신청
            self._urlBranch();
        },

        _addMobileLinks: function(){
            var self = this;

            if(self.$mobileLinks == null){
                var toggleList = [];
                var itemList = {};
                self.$el.find('.link-wrap .link-section div.dep1').each(function(idx, item){
                    if(!$(item).hasClass('hidden')){
                        toggleList.push($(item).clone());
                        var id = $(item).attr("id");
                        itemList[id] = [];
                    }
                });

                self.$el.find('.link-wrap .link-section .dep2-wrap').each(function(idx, item){
                    var id = $(item).data('groupId');
                    $(item).find('> li').each(function(cdx, child){
                        if (itemList[id] instanceof Array) {
                            itemList[id].push($(child).clone());
                        }
                    });
                });


                var elements = "";
                elements += '<ul class="link-wrap ui_footer_accordion">';

                for(var i=0;i<toggleList.length;i++){
                    elements += '   <li class="link-section">';
                    elements += '       <ul role="tree" class="dep2-wrap ui_accord_content" aria-labelledby="depth1-' + (i+1) + '-1">';
                    elements += '       </ul>';
                    elements += '   </li>';
                }

                elements += '</ul>';

                
                lgkorUI.CONTEXT_AREA.find('.cont-area').prepend(elements);
                
                lgkorUI.CONTEXT_AREA.find('.link-wrap.ui_footer_accordion > li').each(function(idx, item){
                    $(toggleList[idx]).addClass('ui_accord_toggle');
                    $(item).prepend($(toggleList[idx]));
                    
                    var id = $(toggleList[idx]).attr("id");

                    var itemListId = itemList[id] ? itemList[id] : {}; 
                    var itemlistleng = $(itemListId[0]).find('ul').length;
                    if(itemlistleng) $(item).find('> ul').addClass('ui_footer_accordion');

                    for(var cdx in itemList[id]){
                        if(itemlistleng){
                            var twoDep = $(itemListId[cdx]).find('> ul').length;
                            if(twoDep > 0){
                                $(itemListId[cdx]).find('> .dep2').addClass('ui_accord_toggle');
                                $(itemListId[cdx]).find('> ul').addClass('ui_accord_content');
                            }                            
                        }

                        $(item).find('> ul').append($(itemListId[cdx]));
                    }

                    if(!itemListId.length){
                        $(item).find('> ul').remove();
                        $(toggleList[idx]).removeClass('ui_accord_toggle');
                    }
                });

                self.$mobileLinks = self.$el.find('.link-wrap.ui_footer_accordion');

                lgkorUI.CONTEXT_AREA.find('.ui_footer_accordion').vcAccordion({
                    singleOpen: true,
                    itemSelector: "> li",
                    toggleSelector: "> .ui_accord_toggle"
                });

                //BTOCSITE-16830 웹접근성 확인 요청_ LGE.com(렌탈케어십 신청/조회, 출장서비스 신청/조회) 접근성 이슈 검토 요청
                lgkorUI.CONTEXT_AREA.find('.ui_footer_accordion .ui_accord_toggle').each(function(idx, item){
                    $(item).find('> a').attr('title', '접힘');
                    $(item).find('> a').on('click', function(e){
                        e.preventDefault();

                        var $this = $(this);
                        if( $('.link-section').hasClass('on')){
                                $this.attr('title', '접힘');
                        } else{
                                $this.attr('title', '펼쳐짐');
                        }
                    })
                });
            }
        },

        _resize: function(){
            var self = this,
                winwidth;

            winwidth = $(window).outerWidth(true);
            if(winwidth > 767){
                if(self.$mobileLinks != null){
                    lgkorUI.CONTEXT_AREA.find('.ui_footer_accordion').vcAccordion('collapseAll');
                    self.$mobileLinks.hide();
                }

                self.$pcLinkes.show();
            } else{
                if(self.prevWinWidth != winwidth) {
                    self.$pcLinkes.hide();
                    self._addMobileLinks();
                    self.$mobileLinks.show();
                }
            }
            self.prevWinWidth = winwidth;
        },

        // LGECOMVIO-192 베스트샵 마이페이지_인테리어상담신청
        _urlBranch: function(){
            var self = this;
            self.$el.find('.link-wrap .link-section a').each(function(idx, item){
                if(item.dataset.pcUrl && item.dataset.mobileUrl) {
                    if(!vcui.detect.isMobile) {
                        item.setAttribute("href", item.dataset.pcUrl);
                    } else {
                        item.setAttribute("href", item.dataset.mobileUrl);
                    }
                }
            })
        }
    });

    return Footer;
});