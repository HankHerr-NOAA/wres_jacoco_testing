package wres.systests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scenario650
{
    private static final Logger LOGGER = LoggerFactory.getLogger( Scenario650.class );
    private static final String NEWLINE = System.lineSeparator();

    /**
     * Expected paths as file names.
     */

    private static final Set<Path> EXPECTED_FILE_NAMES =
            Set.of( Path.of( "76.775925W_39.259094N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.775925W_39.259094N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.775925W_39.259094N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.778915W_39.25013N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.778915W_39.25013N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.778915W_39.25013N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.7819W_39.241165N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.7819W_39.241165N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.7819W_39.241165N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.784515W_39.27037N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.784515W_39.27037N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.784515W_39.27037N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.78489W_39.2322N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.78489W_39.2322N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.78489W_39.2322N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.787506W_39.261406N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.787506W_39.261406N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.787506W_39.261406N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.79049W_39.25244N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.79049W_39.25244N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.79049W_39.25244N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.79347W_39.243477N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.79347W_39.243477N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.79347W_39.243477N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.7961W_39.272682N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.7961W_39.272682N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.7961W_39.272682N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.79646W_39.234512N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.79646W_39.234512N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.79646W_39.234512N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.79908W_39.263718N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.79908W_39.263718N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.79908W_39.263718N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.799446W_39.225548N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.799446W_39.225548N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.799446W_39.225548N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.80207W_39.254753N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.80207W_39.254753N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.80207W_39.254753N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.80505W_39.24579N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.80505W_39.24579N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.80505W_39.24579N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.80768W_39.274994N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.80768W_39.274994N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.80768W_39.274994N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.80804W_39.236824N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.80804W_39.236824N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.80804W_39.236824N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.81066W_39.26603N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.81066W_39.26603N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.81066W_39.26603N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.81101W_39.22786N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.81101W_39.22786N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.81101W_39.22786N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.813644W_39.257065N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.813644W_39.257065N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.813644W_39.257065N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.81663W_39.248096N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.81663W_39.248096N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.81663W_39.248096N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.81961W_39.239132N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.81961W_39.239132N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.81961W_39.239132N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.82224W_39.268337N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.82224W_39.268337N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.82224W_39.268337N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "76.822586W_39.230167N_NWM_Short_Range_MEAN_ERROR.csv" ),
                    Path.of( "76.822586W_39.230167N_NWM_Short_Range_PEARSON_CORRELATION_COEFFICIENT.csv" ),
                    Path.of( "76.822586W_39.230167N_NWM_Short_Range_SAMPLE_SIZE.csv" ),
                    Path.of( "pairs.csv.gz" ),
                    Path.of( "NWM_Short_Range_10800-10800_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_14400-14400_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_18000-18000_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_21600-21600_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_25200-25200_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_28800-28800_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_32400-32400_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_3600-3600_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_36000-36000_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_39600-39600_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_43200-43200_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_46800-46800_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_50400-50400_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_54000-54000_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_57600-57600_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_61200-61200_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_64800-64800_SECONDS.nc" ),
                    Path.of( "NWM_Short_Range_7200-7200_SECONDS.nc" ) );


    private ScenarioInformation scenarioInfo;
    
    /**
     * Watch for any failed assertions and log them.
     */

    @Rule
    public TestWatcher watcher = new TestWatcher()
    {
        @Override
        protected void failed( Throwable e, Description description )
        {
            LOGGER.error( description.toString(), e );
        }
    };

    @Before
    public void beforeIndividualTest() throws IOException, SQLException
    {
        LOGGER.info( "########################################################## EXECUTING "
                     + this.getClass().getSimpleName().toLowerCase()
                     + NEWLINE );
        this.scenarioInfo = new ScenarioInformation( this.getClass()
                                              .getSimpleName()
                                              .toLowerCase(),
                                              ScenarioHelper.getBaseDirectory() );
        ScenarioHelper.logUsedSystemProperties( scenarioInfo );
    }

    @Test
    public void testScenario()
    {
        Set<Path> pathsWritten = ScenarioHelper.executeScenario( scenarioInfo );
        Set<Path> actualFileNamesThatExist = pathsWritten.stream()
                                                         .filter( Files::exists )
                                                         .map( Path::getFileName )
                                                         .collect( Collectors.toSet() );

        // Expected file-name paths equals actual
        LOGGER.info( "Checking expected file names against actual file names that exist for {} files...",
                     EXPECTED_FILE_NAMES.size() );

        assertEquals( "The actual set of file names does not match the expected set of file names."
                      + " These existed in expected, but not in actual: "
                      + new TreeSet<>( Sets.difference( EXPECTED_FILE_NAMES, actualFileNamesThatExist ) )
                      + " while these existed in actual, but not expected: "
                      + new TreeSet<>( Sets.difference( actualFileNamesThatExist, EXPECTED_FILE_NAMES ) ),
                      EXPECTED_FILE_NAMES,
                      actualFileNamesThatExist );

        LOGGER.info( "Finished checking file names. The actual file names match the expected file names." );
        
        ScenarioHelper.assertOutputsMatchBenchmarks( scenarioInfo, pathsWritten );
        LOGGER.info( "########################################################## COMPLETED "
                + this.getClass().getSimpleName().toLowerCase() + NEWLINE);
    }
}

