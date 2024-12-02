import type { Metadata } from 'next';
import localFont from 'next/font/local';
import './globals.css';
import HandleModalSection from '@/components/Modal/HandleModalSection';
import QueryClientProvider from '@/Providers/QueryClientProvider';
import { Toaster } from '@/components/ui/toaster';
import Script from 'next/script';

const geistSans = localFont({
  src: './fonts/GeistVF.woff',
  variable: '--font-geist-sans',
  weight: '100 900',
});
const geistMono = localFont({
  src: './fonts/GeistMonoVF.woff',
  variable: '--font-geist-mono',
  weight: '100 900',
});

export const metadata: Metadata = {
  title: '아파트 거래 검색사이트',
  description: 'Generated by create next app',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${geistSans.variable} ${geistMono.variable} antialiased`}>
        <QueryClientProvider>
          <HandleModalSection />
          {children}
          <Toaster />
        </QueryClientProvider>
        <Script
          src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env
            .NEXT_PUBLIC_KAKAO_MAP_KEY!}&libraries=services,clusterer,drawing&autoload=false`}
          strategy="beforeInteractive"
        />
      </body>
    </html>
  );
}