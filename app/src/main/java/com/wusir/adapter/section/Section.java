package com.wusir.adapter.section;

import com.lzy.okgo.model.Progress;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class Section {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int category_id;
        private List<Progress> downing_data,downed_data;

        public List<Progress> getDowning_data() {
            return downing_data;
        }

        public void setDowning_data(List<Progress> downing_data) {
            this.downing_data = downing_data;
        }

        public List<Progress> getDowned_data() {
            return downed_data;
        }

        public void setDowned_data(List<Progress> downed_data) {
            this.downed_data = downed_data;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

    }
}
