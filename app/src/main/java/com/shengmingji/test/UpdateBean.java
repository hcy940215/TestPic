package com.shengmingji.test;

/**
 * Created by dell on 2017/11/11.
 */

public class UpdateBean {

    /**
     * _header_ : {"success":true,"timeCost":18}
     * avatarId : 8ccfd402-29ce-4c1f-aef5-d2b84eca04cc
     */

    private HeaderBean _header_;
    private String avatarId;

    public HeaderBean get_header_() {
        return _header_;
    }

    public void set_header_(HeaderBean _header_) {
        this._header_ = _header_;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public static class HeaderBean {
        /**
         * success : true
         * timeCost : 18
         */

        private boolean success;
        private int timeCost;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getTimeCost() {
            return timeCost;
        }

        public void setTimeCost(int timeCost) {
            this.timeCost = timeCost;
        }
    }
}

