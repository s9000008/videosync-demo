
//var testSourceRow = { type: "video/mp4", src: "https://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};
const playlistView = new Vue({
	el: '#playlistViewVue',
	data: {
		show:false,
		initFlag:false,
		playIndex:0,
		uid:"",
		playlist:{},
		playlistCount:0,
		videos:[]
	},beforeCreate:function(){

	},created:function(){
		
	},methods: {
		videoDivBuild(resourcePath){
			let uid = this.resourcePath.replace(baseUrl,"");
			this.$refs.videoDiv.innerHTML = `
			<video class="video-js" id="video-screen" preload="auto" muted="true" >  
				<track kind='captions' src="${subtitle_service.getSource}?uid=${uid}" label="Chinese" default />
			</video>`;//<source src="${resourcePath}" type="video/mp4">
			this.initFlag = true;
			this.show = true;
			
			resourcePath = resourcePath.replace("/o/","/");
			videoControlInit(resourcePath, "video/mp4", "");
			//				<track kind='captions' src="https://storage.cloud.google.com/thc_video_storage/resource/%5BSC-OL%5D%5BShakugan%20no%20Shana%20III%5D%5B04%5D%5BMKV%5D%5BX264_FLAC%5D%5BBDRip%5D.big5.ass" label="Chinese" default />

			//"https://storage.cloud.google.com/thc_video_storage//resource/%5BSC-OL%5D%5BShakugan%20no%20Shana%20III%5D%5B04%5D%5BMKV%5D%5BX264_FLAC%5D%5BBDRip%5D.big5.ass?generation=1676723774661294&alt=media");
			
			//"https://storage.cloud.google.com/thc_video_storage/gs%3A//thc_video_storage/%5BSC-OL%5D%5BShakugan%20no%20Shana%20III%5D%5B23%5D%5BMKV%5D%5BX264_FLAC%5D%5BBDRip%5D.big5.ass"
		},
		change:function(i){
			if(videoRow != null){
				videoRow.reset();
			}
			console.log(this.playlist);
			let videos = this.videos;
			if(i < 0 || i >= videos.length){
				i = 0;
			}
			let video = videos[i];
			console.log(video)
			this.resourcePath = `${baseUrl}${video.uid}`;
			//console.log(this.$refs.videoDiv.innerHTML == "");
			if(!this.initFlag){
				this.$refs.videoDiv.innerHTML == "";
				/*
				let videoVudObj = this;
		fetch(`${baseUrl}${queryParams.uid}`,{method:"get"})
		.then(function(response) {
		    return response.json();
		})
		.then(function(resultJson) {
			videoVudObj.videoDivBuild(resultJson);
		    console.log(resultJson);
		});
				
				
				 */
				//let videoVudObj = this;
				fetch(`${baseUrl}${video.uid}`,{method:"get"})
				.then(function(response) {
				    return response.json();
				})
				.then(function(resultJson) {
					playlistView.videoDivBuild(resultJson.item.path);
				});
				
			
					
				/*this.$refs.videoDiv.innerHTML = `
				<video class="video-js" id="video-screen" preload="auto" muted="true" width="1280" height="720" data-setup="{}" controls >
					<source src="${resultJson.item.path}" type="video/mp4" />
				</video>`;*/
				//this.$refs.videoDiv.innerHTML = `<video class="video-js" id="video-screen" preload="auto" muted="true" width="1280" height="720" data-setup="{}" controls ><source src="${this.resourcePath}" type="video/mp4" /></video>`
				
			}else{
				sendChangeAction(video.uid)
				/*this.$refs.videoDiv.getElementsByTagName("source")[0].src = this.resourcePath;
				this.$refs.videoDiv.getElementsByTagName("source")[0].type = "video/mp4";
				videoReload();*/
			}
			
		},
		getAllVideo:function(){
			let response = this.$http.get(playlist_service.get + "?uid=" + this.uid);
			response.then(function(response){
				let body = response.body;
				this.playlist = {};
				console.log(body);
				if(body.success){
					this.playlist.uid = body.item.uid;
					this.playlist.name = body.item.name;
					this.playlist.id = body.item.id;
					this.playlist.itemList = body.item.itemList;
					this.videos = this.playlist.itemList;
					this.change(this.playIndex);
					this.playlistCount = this.videos.length;
				}else{
					alert(body.message);
				}
			},function(){
				
			});
		},
	},mounted:function(){
		const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));
		this.uid = queryParams.uid;
		console.log(this.uid)
		this.getAllVideo();
		this.listFlag = true;
	}
});

