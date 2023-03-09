//取出播放清單
//編輯播放清單
//刪除播放清單
//編輯播放清單的影片
const playlist = new Vue({
	el: '#playlistVue',
	data: {
		playlistCount:0,
		playlist:{name: "", uid: "", id: 0, itemList: []},
		videos:[],
		show:false,
		name:"",
		playlistItems:[],
		listFlag: false,
		createFormFlag: false,
		editFormFlag: false,
		addFormFlag: false,
		uiIndex:[0,0,0,0,0,0,0,0,0,0]
	},beforeCreate:function(){

	},created:function(){
		
	},methods: {
		getAll:function(){
			let response = this.$http.get(playlist_service.all,'/playlist/all');
			response.then(function(response){
				this.playlistItems = response.body;
				this.playlistCount = playlist.playlistItems.length;
			},function(){
				console.log("發生錯誤");
			});
		},
		getAllVideo:function(){
			let videos = this.videos;
			let response = this.$http.get(video_service.all);
			response.then(function(response){
				let body = response.body;
				let itemList = body.itemList;
				itemList.forEach(function(row){
					videos.push({name: row.name, id: row.id, uid: row.uid});
				})
				this.videos = videos;
			},function(){
			});
		},
		get:function(){
			
		},
		save:function(){
			let response = this.$http.post(playlist_service.save,{name:this.name});
			response.then(function(response){
				let body = response.body;
				if(body.success){
					alert(body.message);
				}else{
					alert(body.message);
				}
				history.go(0);
			},function(){
				
			});
		},
		edit:function(){
			
		},
		delete:function(){
			
		},
		add:function(){
			let vid = document.getElementsByName("videoId")[0].value
			let pid = this.playlist.id;
			let response = this.$http.post(playlist_service.add,{pid: pid.toString(), vid: vid.toString(), sort: 0});
			response.then(function(response){
				let body = response.body
				if(body.success){
					this.reloadEdit();
				}
				alert(body.message);
			},function(){
				
			});
		},
		remove:function(aid){
			
			console.log(aid)
			let response = this.$http.post(playlist_service.remove,{id: aid});
			response.then(function(response){
				let body = response.body
				if(body.success){
					this.reloadEdit();
				}
				alert(body.message);
			},function(){
				
			});
		},
		reloadEdit:function(){
			console.log("reoladEdit");
			let response = this.$http.get(playlist_service.get + "?uid=" + this.playlist.uid);
			response.then(function(response){
				let body = response.body;
				this.playlist = {};
				if(body.success){
					this.playlist.uid = body.item.uid;
					this.playlist.name = body.item.name;
					this.playlist.id = body.item.id;
					this.playlist.itemList = body.item.itemList;
				}else{
					alert(body.message);
				}
			},function(){
				
			});
		},
		hiddenAll:function(){
			this.listFlag = false;
			this.createFormFlag = false;
			this.editFormFlag = false;
			this.addFormFlag = false;
		},
		showPlaylist:function(){
			this.hiddenAll();
			this.listFlag = true;
		},
		showEdit:function(){
			this.hiddenAll();
			this.editFormFlag = true;
		},
		createAction:function(){
			console.log("createAction");
			this.hiddenAll();
			this.createFormFlag = true;
		},
		editAction:function(uid){
			console.log("editAction");
			let response = this.$http.get(playlist_service.get + "?uid=" + uid);
			response.then(function(response){
				let body = response.body;
				console.log(body);
				if(body.success){
					this.playlist.uid = body.item.uid;
					this.playlist.name = body.item.name;
					this.playlist.id = body.item.id;
					this.playlist.itemList = body.item.itemList;
					
				}else{
					alert(body.message);
				}
			},function(){
				
			});
			this.hiddenAll();
			this.editFormFlag = true;
		},
		addAction:function(){
			this.hiddenAll();
			this.addFormFlag = true;
		},
		removeAction:function(){
			
		},
		sort:function(aid, sort){
			let response = this.$http.post(playlist_service.sort, {aid: aid, sort: sort});
			response.then(function(response){
				let body = response.body;
				if(body.success){
					this.reloadEdit();
				}else{
					alert(body.message);
				}
			},function(){
				
			});
		},
		play:function(){
		    console.log(this.playlist.uid)
		    console.log(this.playlist.id)
		    document.location.href="playlist-view.html?uid=" + this.playlist.uid;
		}
	},mounted:function(){
		this.getAll();
		this.getAllVideo();
		this.listFlag = true;
	}
});

