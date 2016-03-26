package com.cheenar.jedsby.parse.encryption;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Cheenar
 * @description PFetchCryptData: Handles all the crypt data for the form key
 */

//TODO: REDO THIS ENTIRE SYSTEM

public class PFetchCryptData
{

    private String compiled;
    private int unid;
    public List<PFetchCryptDataSlices> slices;

    public String getCompiled()
    {
        return compiled;
    }

    public int getUnid()
    {
        return unid;
    }

    public PFetchCryptDataSlices getSlices()
    {
        return slices.get(0);
    }

    public class PFetchCryptDataSlices
    {
        private String nid;
        @SerializedName("_formkey")
        private String formKey;
        private String version;
        private PFetchCryptDataSlicesData data;

        public String getNid()
        {
            return nid;
        }

        public String getFormKey()
        {
            return formKey;
        }

        public String getVersion()
        {
            return version;
        }

        public PFetchCryptDataSlicesData getData()
        {
            return data;
        }
    }

    public class PFetchCryptDataSlicesData
    {
        private int nid;
        private int nodetype;
        private int nodesubtype;
        private String sauthdata;

        public int getNid()
        {
            return nid;
        }

        public int getNodetype()
        {
            return nodetype;
        }

        public int getNodesubtype()
        {
            return nodesubtype;
        }

        public String getSauthdata()
        {
            return sauthdata;
        }
    }

}
