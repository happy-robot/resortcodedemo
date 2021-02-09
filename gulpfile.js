const gulp = require('gulp'),
    sass = require('gulp-sass'),
    autoprefixer = require('gulp-autoprefixer'),
    njkRender = require('gulp-nunjucks-render'),
    htmlPrettify = require('gulp-html-prettify'),
    rtlcss = require('gulp-rtlcss'),
    rename = require('gulp-rename'),
    cssnano = require('gulp-cssnano'),
    concat = require('gulp-concat'),
    purgecss = require('gulp-purgecss'),
    del = require('del'),
    browserSync = require('browser-sync').create(),
    imagemin = require('gulp-imagemin'),
    cache = require('gulp-cache'),
    uglify = require('gulp-uglify'),
    spritesmith = require('gulp.spritesmith'),
    merge = require('merge-stream'),
    buffer = require('vinyl-buffer');


function sassMain() {
    return gulp.src('./src/main/resources/static/unify/assets/include/scss/**/*.scss')
        .pipe(sass({outputStyle: 'expanded'}))
        .pipe(autoprefixer(['last 5 versions', '> 1%'], {cascade: true}))
        .pipe(gulp.dest('./src/main/resources/static/unify/assets/css/'));
}

function sassEc() {
    return gulp.src('./src/main/resources/static/unify/e-commerce/assets/scss/**/*.scss')
        .pipe(sass({outputStyle: 'expanded'}))
        .pipe(autoprefixer(['last 5 versions', '> 1%'], {cascade: true}))
        .pipe(gulp.dest('./src/main/resources/static/unify/e-commerce/assets/css/'));
}

//будет 2 файла css - основные стили app.min.css (подгружается как можно быстрее)
//и vendor.min.css - стили дополнительных библиотек, подгружаются в конце
gulp.task('css', gulp.series(sassMain, sassEc, gulp.parallel(appCss, vendorCss)));

function appCss() {
    return gulp.src([
        './src/main/resources/static/unify/assets/vendor/bootstrap/bootstrap.min.css',
        './src/main/resources/static/unify/assets/vendor/hamburgers/hamburgers.min.css',
        './src/main/resources/static/unify/e-commerce/assets/css/styles.e-commerce.css',
        './src/main/resources/static/unify/e-commerce/assets/css/custom.css',
        './src/main/resources/static/unify/assets/css/sprites.css',
    ], {allowEmpty: true})
        .pipe(concat('app.min.css'))
        //https://www.npmjs.com/package/gulp-purgecss
        // .pipe(purgecss({                            //удаляем неиспользованные стили в указанных страницах
        //     content: ['./src/main/resources/templates/tenant/mobile/index.html']
        // }))

        .pipe(cssnano())
        .pipe(gulp.dest('./src/main/resources/static/build/css/'));
}

function vendorCss(cb) {
    cb();
}

function clean() {
    return del(['./src/main/resources/static/build/'])
}

gulp.task('js', gulp.parallel(js, vendorJs));

function js() {
    return gulp.src([
        './src/main/resources/static/unify/assets/vendor/jquery/jquery.min.js',
        './src/main/resources/static/unify/assets/vendor/jquery-migrate/jquery-migrate.min.js',
        './src/main/resources/static/unify/assets/vendor/popper.js/popper.min.js',
        './src/main/resources/static/unify/assets/vendor/bootstrap/bootstrap.min.js',
        './src/main/resources/static/unify/assets/vendor/bootstrap/offcanvas.js'])
        .pipe(uglify())
        .pipe(concat('app.min.js'))
        .pipe(gulp.dest('./src/main/resources/static/build/js/'));
}

function vendorJs() {
    return gulp.src([
        './src/main/resources/static/unify/assets/js/hs.core.js',
        './src/main/resources/static/unify/assets/js/components/hs.header.js',
        './src/main/resources/static/unify/assets/js/helpers/hs.hamburgers.js',
        './src/main/resources/static/unify/assets/js/components/hs.navigation.js',
        './src/main/resources/static/unify/assets/js/components/hs.go-to.js',
    ])
        .pipe(uglify())
        .pipe(concat('vendor.min.js'))
        .pipe(gulp.dest('./src/main/resources/static/build/js/'));
}

//
// Watch
//

gulp.task('watch', function () {
    browserSync.init({proxy: 'localhost:8080',});
    gulp.watch(['./src/main/resources/static/unify/**/*.scss'], gulp.series('css', 'sprite', updateInstanceCss, reload));
    gulp.watch(['./src/main/resources/static/unify/**/*.js'], gulp.series(js));
    gulp.watch(['./src/main/resources/static/img/**/*.+(png|jpg|gif|svg)'], gulp.series(images, 'sprite'));

    gulp.watch(['src/main/resources/messages*.properties'], gulp.series(updateInstanceLocaleMessages, reload));
    gulp.watch(['src/main/resources/**/*.html'], gulp.series(updateInstanceHtml, reload));
    // gulp.watch(['src/main/resources/**/*.css'], gulp.series(updateInstanceCss, reload));
    gulp.watch(['src/main/resources/**/*.js'], gulp.series(updateInstanceJs, reload));
    gulp.watch(['./src/main/resources/static/build/img/**/*.+(png|jpg|gif|svg)'], gulp.series(updateInstanceImages, reload));

});

gulp.task('update-instance', gulp.parallel(updateInstanceLocaleMessages, updateInstanceHtml, updateInstanceCss,
    updateInstanceJs, updateInstanceImages, reload));


function updateInstanceLocaleMessages() {
    return gulp.src(['src/main/resources/messages*.properties']).pipe(gulp.dest('target/classes/'));
}

function updateInstanceHtml() {
    return gulp.src(['src/main/resources/**/*.html']).pipe(gulp.dest('target/classes/'));
}

function reload(done) {
    browserSync.reload();
    done();
}

function updateInstanceCss() {
    return gulp.src(['src/main/resources/**/*.css']).pipe(gulp.dest('target/classes/'));
}

function updateInstanceJs() {
    return gulp.src(['src/main/resources/**/*.js']).pipe(gulp.dest('target/classes/'));
}

function updateInstanceImages() {
    return gulp.src(['./src/main/resources/static/build/**/*.+(png|jpg|gif|svg)']).pipe(gulp.dest('target/classes/static/build/'));
}

gulp.task('images', images);

function images(){
    return gulp.src('./src/main/resources/static/img/**/*.+(png|jpg|gif|svg)', '!./src/main/resources/static/img/sprite/**/*.png')
        .pipe(cache(imagemin()))
        .pipe(gulp.dest('./src/main/resources/static/build/img/'));
}

function clearCache(callback) {
    return cache.clearAll(callback)
}

gulp.task('sprite', function () {
    var spriteData = gulp.src('./src/main/resources/static/img/sprite/*.png').pipe(spritesmith({
        imgName: 'sprites.png',
        cssName: 'sprites.css'
    }));
    var imgStream = spriteData.img
        .pipe(buffer())
        .pipe(imagemin())
        .pipe(gulp.dest('./src/main/resources/static/build/css/'));
    var cssStream = spriteData.css
        .pipe(gulp.dest('./src/main/resources/static/unify/assets/css/'));
    // return spriteData.pipe(gulp.dest('./src/main/resources/static/build/img/'));
    return merge(imgStream, cssStream);
});

//
// Watch
//

// gulp.task('watch', function() {
//     gulp.watch(['./src/main/resources/static/unify/assets/include/scss/**/*.scss'], ['sass-main']);
//     gulp.watch('./src/main/resources/static/unify/assets/include/nunjucks/**/*.njk', ['njk-main']);
// });

gulp.task('build', gulp.series(clean, clearCache, 'sprite', gulp.parallel('css', 'js', 'images')));

gulp.task('default', gulp.series('watch'));
