/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao;

import org.hibernate.type.Type;

/**
 * Used to locate any specific type mappings that might be necessary for a dao implementation
 */
public interface FinderArgumentTypeFactory
{
    Type getArgumentType(Object arg);
}