function generator(N, L)
    thetas = ones(N, 1);
    for i = 1:N
        thetas(i) = rand * (2 * pi);
    end
    v = ones(N,1);

    x = ones(N,1);
    y = ones(N,1);

    for i = 1:N
        x(i) = rand * L;
        y(i) = rand * L;
    end


    fname = '../files/dynamicFile.txt';
    fid = fopen (fname, "w");
    fprintf(fid, '%d\n', L);
    fprintf(fid, '%f %f %f\n', [x, y, thetas]');
    fclose (fid);
end

