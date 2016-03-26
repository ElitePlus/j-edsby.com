package com.cheenar.jedsby.parse.login;

import java.util.List;

/**
 * @note God this was the hardest bit of crap to ever work with, never again...
 */

public class Classes
{

    private List<ClassesData> slices;

    public ClassesData getData()
    {
        return slices.get(0);
    }

    public class ClassesData
    {

        private ClassesDataData data;

        public ClassesDataData getData()
        {
            return data;
        }

        public class ClassesDataData
        {
            private int nid;
            private ClassHolder col1;

            public int getNid()
            {
                return nid;
            }

            public ClassHolder getContainer()
            {
                return col1;
            }
        }

        public class ClassHolder
        {
            private  ClassContainer classes;
            public  ClassContainer getClasses()
            {
                return classes;
            }
        }

        public class ClassContainer
        {
            private int havereportcards;


            public int getHavereportcards()
            {
                return this.havereportcards;
            }

            private ClassContainerClass classesContainer;

            public ClassContainerClass getClassesContainer()
            {
                return classesContainer;
            }

            public class ClassContainerClass
            {
                private Object classes;
                public Object getClasses()
                {
                    return classes;
                }
            }

        }

    }

}
