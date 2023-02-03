package com.example.season6.app;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class app {

    public static class main{
        public static final String Tag = "Season 6";
        public static final String DB_NAME           = Tag + ".db";
        public static final int    DB_VERSION        = 1;
        public static final String NOTE_TABLE        = "NOTE";
        public static final String TABLE_ID          = "ID";
        public static final String TABLE_MESSAGE     = "MESSAGE";
        public static final String TABLE_NAME        = "NAME";
        public static final String TABLE_SEEN        = "SEEN";
        public static final String TABLE_POSITION    = "POSITION";



        //Product Table:
        public static final String PRODUCTS_TABLE    = "Products";
        public static final String PRODUCT_ID        = "ProductID";
        public static final String PRODUCT_NAME      = "ProductName";
        public static final String SUPPLIER_ID       = "SupplierID";
        public static final String CATEGORY_ID       = "CategoryID";
        public static final String UNIT              = "Unit";
        public static final String PRICE             = "Price";


    }





    public static class tables{
        public static final String CUSTOMERS         = "Customers";
        public static final String ORDERS            = "Orders";
        public static final String ORDERS_DETAILS    = "OrderDetails";
    }






    public static class CUSTOMERS{

        public static final String CUSTOMER_NAME = "CustomerName";
        public static final String CUSTOMER_ID = "CustomerID";
    }

    public static class ORDERS{

        public static final String ORDER_ID      = "OrderID";
        public static final String CUSTOMER_ID = "CustomerID";

    }














    public static void l(String message){
        Log.e(main.Tag , message);
    }



    public static void t(String message){
        Toast.makeText(Application.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
