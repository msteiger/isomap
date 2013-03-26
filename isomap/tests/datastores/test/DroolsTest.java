/*
 * Copyright (C) 2012-2013 Martin Steiger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package datastores.test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.ReturnValueDescr;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.lang.DrlDumper;
import org.drools.lang.api.DescrFactory;
import org.drools.lang.descr.AndDescr;
import org.drools.lang.descr.BaseDescr;
import org.drools.lang.descr.BindingDescr;
import org.drools.lang.descr.PackageDescr;
import org.drools.lang.descr.RuleDescr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class DroolsTest
{
	private static final Logger log = LoggerFactory.getLogger(DroolsTest.class);
	
	public static void main(String[] args) throws IOException
	{
		PackageDescr pkg = DescrFactory.newPackage()
		    .name("facts")
		    .newImport().target("java.util.List").end()
		    .newImport().target("common.GridData").end()
		    .newImport().target("terrain.Tile").end()
		    .newImport().target("tiles.TileIndex").end()
		    .newGlobal().type("GridData").identifier("indexData").end()
		    .newGlobal().type("TileIndex").identifier("invalidTile").end()
            // rule
            .newRule()
            	.name( "test" )
            	.attribute("dialect", "mvel")
                .lhs().pattern("$tile : Tile").constraint("$x : mapX, $y : mapY").end()
                .end()
                .rhs("\tList<TileIndex> list = (List)indexData.getData($x, $y);\n" +
                		"\tlist.add(invalidTile);\n")
                .end()
		   .getDescr();
	
		compilePkgDescr(pkg);
        
        Path drlPath = Paths.get("test.drl");
		String dump = new DrlDumper().dump(pkg);
//		System.out.println(dump);
		
		try (BufferedWriter writer = Files.newBufferedWriter(drlPath, Charset.defaultCharset()))
		{
			writer.write(dump);
		}
	}
	
//	private RuleDescr addRule()
//	{
//		final RuleDescr ruleDescr = new RuleDescr( "rule-1" );
//
//        final AndDescr lhs = new AndDescr();
//        ruleDescr.setLhs( lhs );
//
//        final ColumnDescr column = new ColumnDescr( Cheese.class.getName(),
//                                                    "stilton" );
//        lhs.addDescr( new BaseDescr() );
//
//        BindingDescr fieldBindingDescr = new BindingDescr( "price",
//                                                                     "x" );
//        column.addDescr( fieldBindingDescr );
//        fieldBindingDescr = new BindingDescr( "price","y" );
//        column.addDescr( fieldBindingDescr );
//
//        final ReturnValueDescr returnValue = new ReturnValueDescr( "price",
//                                                                   "==",
//                                                                   expression );
//        column.addDescr( returnValue );
//
//        ruleDescr.setConsequence( "modify(stilton);" );
//        
//        return ruleDescr;
//	}
	
	private static Collection<KnowledgePackage> compilePkgDescr(PackageDescr pkg) 
	{
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add( ResourceFactory.newDescrResource( pkg ),
                      ResourceType.DESCR );

        if (kbuilder.hasErrors())
        {
        	KnowledgeBuilderErrors errors = kbuilder.getErrors();
        	for (KnowledgeBuilderError error : errors)
        	{
        		log.error(error.getSeverity() + " " + Arrays.toString(error.getLines()) + error.getMessage());
        	}
        }
        
        return kbuilder.getKnowledgePackages();
    }
}
