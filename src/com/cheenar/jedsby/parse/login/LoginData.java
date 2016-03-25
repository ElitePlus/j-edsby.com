package com.cheenar.jedsby.parse.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 3/25/16.
 */
public class LoginData
{

    private String compiled;
    private String unid;
    private List<LoginDataSlice> slices;

    public String getCompiled()
    {
        return compiled;
    }

    public String getUnid()
    {
        return unid;
    }

    public LoginDataSlice getSlice()
    {
        return this.slices.get(0);
    }

    public class LoginDataSlice
    {
        private LoginDataSliceData data;
        private int nid;
        @SerializedName("_formkey")
        private String formKey;

        public LoginDataSliceData getData()
        {
            return data;
        }

        public int getNid()
        {
            return nid;
        }

        public String getFormKey()
        {
            return formKey;
        }
    }

    public class LoginDataSliceData
    {
        private int nid;
        private String name;
        private String guid;
        //private LoginDataSliceDataSchools schools; //THIS IS PROBABLY BROKEN

        public int getNid()
        {
            return nid;
        }

        public String getName()
        {
            return name;
        }

        public String getGuid()
        {
            return guid;
        }

        public class LoginDataSliceDataSchools
        {
            private LoginDataSliceDataSchool r6427225;
            private LoginDataSliceDataSchool r15995;

            public LoginDataSliceDataSchool getR6427225()
            {
                return r6427225;
            }

            public LoginDataSliceDataSchool getR15995()
            {
                return r15995;
            }
        }

        public class LoginDataSliceDataSchool
        {
            private int nid;
            private String school;

            public int getNid()
            {
                return nid;
            }

            public String getSchool()
            {
                return school;
            }
        }

    }

}
