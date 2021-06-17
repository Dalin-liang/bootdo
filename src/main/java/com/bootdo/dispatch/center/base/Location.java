package com.bootdo.dispatch.center.base;

import java.math.BigDecimal;

/**
 * 经纬度定位接口
 * @author JunoGuan
 * @version 1.0.0
 * @create 2019/8/19
 */
public interface Location {

    /**
     * 纬度
     * @return
     */
    BigDecimal getLat();

    /**
     * 经度
     * @return
     */
    BigDecimal getLon();

    class SimpleLocation implements Location{

        private BigDecimal lat;
        private BigDecimal lon;

        public SimpleLocation(double lon,double lat) {
            this.lon = new BigDecimal(lon);
            this.lat = new BigDecimal(lat);
        }

        public SimpleLocation(String[] location) {
            this.lon = new BigDecimal(location[0]);
            this.lat = new BigDecimal(location[1]);

        }

        public SimpleLocation(String lon,String lat) {
            this.lon = new BigDecimal(lon);
            this.lat = new BigDecimal(lat);
        }

        public SimpleLocation(BigDecimal lon,BigDecimal lat) {
            this.lon = lon;
            this.lat = lat;
        }

        @Override
        public BigDecimal getLat() {
            return lat;
        }

        @Override
        public BigDecimal getLon() {
            return lon;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("SimpleLocation{");
            sb.append("lat=").append(lat);
            sb.append(", lon=").append(lon);
            sb.append('}');
            return sb.toString();
        }
    }

}
