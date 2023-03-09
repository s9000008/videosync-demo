const videoListVue = new Vue({
	el: '#videoListVue',
	data: {
		show:false,
		videos:[]
	},beforeCreate:function(){

	},created:function(){
		
	},methods: {
		watch:function(video){
			document.location.href = `/video.html?uid=${video.uid}&ext=${video.extension}`;
		},
		load:function(){
		    console.log(video_service.all)
			let response = this.$http.get(video_service.all);
			response.then(function(response){
				let body = response.body;
				let itemList = body.itemList;
				itemList.forEach((row, idx) =>{
					let videoRow = {uid: row.uid, name: row.path, extension: row.extension, size: row.size};
					this.videos.push(videoRow);
				});
				
				/*this.httpCode = response.status;
				if(response.status == 200){
					let message = "註冊成功";
					if(!responseBody.success){
						message = responseBody.message;
						history.go(0);
					}else{
						alert(message);
						document.location.href="/";
					}
				}*/
			},function(){
				console.log("發生錯誤");
			})
			this.show = true;
		}
	},mounted:function(){
		console.log("TEST")
		this.load();
		console.log(this.show)
	}
})

