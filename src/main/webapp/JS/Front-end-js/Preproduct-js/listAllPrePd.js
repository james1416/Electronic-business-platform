let len = 40;
    $(".carddesc").each(function(i){
          if($(this).text().length>len){
          $(this).attr("title", $(this).text());
          let text  = $(this).text().substring(0, len-1)+"...";
          $(this).text(text);
   }
})