INSERT INTO product_images (
    id,
    product_id,
    image_url,
    alt_text,
    is_main,
    sort_order
) VALUES
      (
          '22222222-2222-2222-2222-222222222221',
          '11111111-1111-1111-1111-111111111111',
          'https://example.com/images/california-love-front.jpg',
          'Front image of Premium California Love',
          TRUE,
          0
      ),
      (
          '22222222-2222-2222-2222-222222222222',
          '11111111-1111-1111-1111-111111111111',
          'https://example.com/images/california-love-side.jpg',
          'Side image of Premium California Love',
          FALSE,
          1
      ),
      (
          '33333333-3333-3333-3333-333333333331',
          '22222222-2222-2222-2222-222222222222',
          'https://example.com/images/purple-haze-front.jpg',
          'Front image of Purple Haze',
          TRUE,
          0
      ),
      (
          '33333333-3333-3333-3333-333333333332',
          '22222222-2222-2222-2222-222222222222',
          'https://example.com/images/purple-haze-side.jpg',
          'Side image of Purple Haze',
          FALSE,
          1
      );