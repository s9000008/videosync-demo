package com.videosync.entity.channel;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import javax.persistence.*;
@Entity
@Data
@NoArgsConstructor
@Table(name="channel")
public class Channel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(unique = true)
    private String code;

	//private Date createdTime;
}
