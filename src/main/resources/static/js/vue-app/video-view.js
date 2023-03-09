//const baseUrl = "/video/resource/watch/";
///video/resource/watch/mao.mp4
const videoVue = new Vue({
	el: '#video-player',
	data: {
		video:{name:"UN",extension:""},
		resourcePath:"",
		show:false,
		videos:[]
	},beforeCreate:function(){
		
	},created:function(){
		
	},methods: {
		change:function(){
			
		},
		videoDivBuild:function(resultJson){
			console.log(resultJson)
			this.$refs.videoDiv.innerHTML = `
				<video class="video-js" id="video-screen" preload="auto" muted="true" width="1280" height="720" data-setup="{}" controls >
					<source src="${resultJson.item.path}" type="video/mp4" />
				</video>`;
		
			this.show = true;
			videoControlInit();
		}
	},mounted:function(){
		const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));
		this.extension = queryParams.ext;
		this.resourcePath = `${baseUrl}${queryParams.uid}`;
		console.log(this.resourcePath);
		let videoVudObj = this;
		fetch(`${baseUrl}${queryParams.uid}`,{method:"get"})
		.then(function(response) {
		    return response.json();
		})
		.then(function(resultJson) {
			videoVudObj.videoDivBuild(resultJson);
		    console.log(resultJson);
		});
	}
})

