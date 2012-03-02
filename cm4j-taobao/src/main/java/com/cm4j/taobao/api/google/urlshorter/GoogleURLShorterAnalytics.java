package com.cm4j.taobao.api.google.urlshorter;

import com.cm4j.core.utils.JsonBinder;

public class GoogleURLShorterAnalytics {
	private String kind;
	private String id;
	private String longUrl;
	private String status;
	private String created;
	private String month;
	private String week;
	private String day;
	private String twoHours;
	private analytics analytics;

	public static class analytics {
		private allTime allTime;

		public allTime getAllTime() {
			return allTime;
		}

		public void setAllTime(allTime allTime) {
			this.allTime = allTime;
		}
	}

	public static class allTime {
		private String shortUrlClicks;
		private String longUrlClicks;
		private String[] countries;
		private String[] browsers;
		private String[] platforms;

		private referrers[] referrers;

		public String getShortUrlClicks() {
			return shortUrlClicks;
		}

		public void setShortUrlClicks(String shortUrlClicks) {
			this.shortUrlClicks = shortUrlClicks;
		}

		public String getLongUrlClicks() {
			return longUrlClicks;
		}

		public void setLongUrlClicks(String longUrlClicks) {
			this.longUrlClicks = longUrlClicks;
		}

		public String[] getCountries() {
			return countries;
		}

		public void setCountries(String[] countries) {
			this.countries = countries;
		}

		public String[] getBrowsers() {
			return browsers;
		}

		public void setBrowsers(String[] browsers) {
			this.browsers = browsers;
		}

		public String[] getPlatforms() {
			return platforms;
		}

		public void setPlatforms(String[] platforms) {
			this.platforms = platforms;
		}

		public referrers[] getReferrers() {
			return referrers;
		}

		public void setReferrers(referrers[] referrers) {
			this.referrers = referrers;
		}
	}

	public static class referrers {
		private Long count;
		private Long id;

		public Long getCount() {
			return count;
		}

		public void setCount(Long count) {
			this.count = count;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTwoHours() {
		return twoHours;
	}

	public void setTwoHours(String twoHours) {
		this.twoHours = twoHours;
	}

	public analytics getAnalytics() {
		return analytics;
	}

	public void setAnalytics(analytics analytics) {
		this.analytics = analytics;
	}

	public static void main(String[] args) {
		allTime allTime = new allTime();
		allTime.setReferrers(new referrers[] { new referrers(), new referrers() });

		analytics analytics = new analytics();
		analytics.setAllTime(allTime);

		GoogleURLShorterAnalytics pojo = new GoogleURLShorterAnalytics();
		pojo.setAnalytics(analytics);

		JsonBinder binder = JsonBinder.ALWAYS;
		System.out.println(binder.toJson(pojo));
	}
}
