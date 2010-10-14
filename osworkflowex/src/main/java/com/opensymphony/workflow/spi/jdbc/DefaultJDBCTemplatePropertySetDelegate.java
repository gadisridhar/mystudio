package com.opensymphony.workflow.spi.jdbc;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.module.propertyset.PropertySetManager;
import com.opensymphony.module.propertyset.database.DefaultJDBCTemplateConfigurationProvider;
import com.opensymphony.workflow.util.PropertySetDelegate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:cucuchen520@yahoo.com.cn">Chris Chen</a>
 */
public class DefaultJDBCTemplatePropertySetDelegate implements PropertySetDelegate {
    //~ Instance fields ////////////////////////////////////////////////////////

    private DataSource dataSource;

    //~ Constructors ///////////////////////////////////////////////////////////

    public DefaultJDBCTemplatePropertySetDelegate() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public PropertySet getPropertySet(long entryId) {
        Map args = new HashMap(1);
        args.put("globalKey", "osff_temp_" + entryId);

        DefaultJDBCTemplateConfigurationProvider configurationProvider = new DefaultJDBCTemplateConfigurationProvider();
        configurationProvider.setDataSource(getDataSource());

        args.put("configurationProvider", configurationProvider);

        return PropertySetManager.getInstance("jdbcTemplate", args);
    }

    private DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
