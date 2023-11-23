package com.trip.penguin.user.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.trip.penguin.alert.domain.AlertMS;
import com.trip.penguin.badge.domain.UserBadge;
import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.wish.domain.WishlistMS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_MS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Builder.Default
	@OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<FollowMS> followList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserBadge> userBadgeList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<WishlistMS> wishlistList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CsMS> csList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "userMs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AlertMS> alertList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BookingMS> bookingList = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReviewMS> reviewMSList = new ArrayList<>();

	@Column(name = "user_first")
	private String userFirst;

	@Column(name = "user_last")
	private String userLast;

	@Column(name = "user_email", nullable = false)
	private String userEmail;

	@Column(name = "user_pwd")
	private String userPwd;

	@Column(name = "user_nick", nullable = false, length = 100)
	private String userNick;

	@Column(name = "user_img")
	private String userImg;

	@Column(name = "user_city")
	private String userCity;

	@Column(name = "social_provider")
	private String socialProvider;

	@Column(name = "social_provider_id")
	private String socialProviderId;

	@Column(name = "user_role", nullable = false)
	private String userRole;

	@Column(name = "off_yn", nullable = false)
	private String offYn;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

	public void createUserMs() {
		this.setCreatedDate(LocalDateTime.now());
		this.setModifiedDate(LocalDateTime.now());
	}

	public void addReview(ReviewMS reviewMS) {
		this.reviewMSList.add(reviewMS);
		reviewMS.setUserMS(this);
	}

	public boolean isOAuthUser() {
		return !OAuthVendor.DEFAULT.getOAuthVendor().equals(this.getSocialProvider());
	}
}