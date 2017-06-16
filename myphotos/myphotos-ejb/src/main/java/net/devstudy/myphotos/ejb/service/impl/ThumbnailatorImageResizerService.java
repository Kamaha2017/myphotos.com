/*
 * Copyright 2017 </>DevStudy.net.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.devstudy.myphotos.ejb.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.enterprise.context.ApplicationScoped;
import net.coobird.thumbnailator.Thumbnails;
import static net.coobird.thumbnailator.geometry.Positions.CENTER;
import net.devstudy.myphotos.common.config.ImageCategory;
import net.devstudy.myphotos.ejb.service.ImageResizerService;
import net.devstudy.myphotos.exception.ApplicationException;

/**
 * 
 * 
 * @author devstudy
 * @see http://devstudy.net
 */
@ApplicationScoped
public class ThumbnailatorImageResizerService implements ImageResizerService{

    @Override
    public void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory) {
        try {
            Thumbnails.Builder<File> builder = Thumbnails.of(sourcePath.toFile());
            if(imageCategory.isCrop()) {
                builder.crop(CENTER);
            }
            builder.size(imageCategory.getWidth(), imageCategory.getHeight())
                    .outputFormat(imageCategory.getOutputFormat())
                    .outputQuality(imageCategory.getQuality())
                    .allowOverwrite(true)
                    .toFile(destinationPath.toFile());
        } catch (IOException ex) {
            throw new ApplicationException("Can't resize image: "+ex.getMessage(), ex);
        }
    }
}
