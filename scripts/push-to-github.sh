#!/bin/bash
# ================================================
# 🚀 MO GitHub Setup Script
# ================================================
# هذا السكريبت يرفع الكود على GitHub تلقائياً
# شغّله مرة واحدة بعد ما تنشئ الـ repo

set -e

# ألوان للإخراج
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}🚀 MO GitHub Setup${NC}"
echo "================================"

# 1. تأكد إننا داخل مجلد المشروع
if [ ! -f "build.gradle.kts" ]; then
  echo -e "${RED}❌ خطأ: شغّل السكريبت من داخل مجلد mo-android${NC}"
  exit 1
fi

# 2. اطلب اسم المستخدم والـ repo
echo ""
echo -e "${YELLOW}📝 معلومات GitHub:${NC}"
read -p "GitHub username (e.g. ahmed): " GITHUB_USER
read -p "Repo name (default: mo-android): " REPO_NAME
REPO_NAME=${REPO_NAME:-mo-android}

echo ""
echo -e "${BLUE}ℹ️  هترفع على: https://github.com/${GITHUB_USER}/${REPO_NAME}${NC}"
read -p "اكمل؟ (y/n): " CONFIRM
if [ "$CONFIRM" != "y" ]; then
  echo "❌ تم الإلغاء"
  exit 0
fi

# 3. تأكد إن Git مثبّت
if ! command -v git &> /dev/null; then
  echo -e "${RED}❌ Git مش مثبّت. حمّله من https://git-scm.com${NC}"
  exit 1
fi

# 4. Init git
echo ""
echo -e "${BLUE}📦 تهيئة Git...${NC}"
if [ ! -d ".git" ]; then
  git init
  git branch -M main
fi

# 5. تأكد من .gitignore
if [ ! -f ".gitignore" ]; then
  echo -e "${RED}❌ ملف .gitignore مش موجود!${NC}"
  exit 1
fi

# 6. أضف remote
echo -e "${BLUE}🔗 ربط GitHub...${NC}"
git remote remove origin 2>/dev/null || true
git remote add origin "https://github.com/${GITHUB_USER}/${REPO_NAME}.git"

# 7. تأكد إن local.properties متجاهل
echo -e "${BLUE}🔐 حماية local.properties...${NC}"
if grep -q "local.properties" .gitignore; then
  echo "✅ local.properties في .gitignore"
else
  echo "local.properties" >> .gitignore
fi

# 8. أضف كل الملفات
echo -e "${BLUE}📂 إضافة الملفات...${NC}"
git add .

# 9. اعمل commit
echo -e "${BLUE}💾 Commit...${NC}"
git commit -m "🎉 Initial MO app commit

- Android native (Kotlin + Jetpack Compose)
- Gemini API integration
- AI Cascade (5 providers)
- Dark theme + RTL Arabic
- GitHub Actions for auto-build" 2>/dev/null || echo "ℹ️  لا تغييرات جديدة"

# 10. Push
echo ""
echo -e "${YELLOW}⚠️  الخطوة التالية:${NC}"
echo -e "1. روح https://github.com/${GITHUB_USER}/${REPO_NAME}"
echo -e "2. تأكد إن الـ repo موجود (ممكن تحتاج تنشئه أولاً من الموقع)"
echo -e "3. شغّل: ${GREEN}git push -u origin main${NC}"
echo ""
echo -e "أو لو تبيني أحاول الـ push الحين:"
read -p "جرب push؟ (y/n): " DO_PUSH

if [ "$DO_PUSH" = "y" ]; then
  git push -u origin main
  echo ""
  echo -e "${GREEN}✅ تم الرفع!${NC}"
  echo ""
  echo -e "${YELLOW}📋 الخطوة التالية:${NC}"
  echo "1. روح https://github.com/${GITHUB_USER}/${REPO_NAME}/settings/secrets/actions"
  echo "2. اضغط 'New repository secret'"
  echo "3. أضف: GEMINI_API_KEY = (مفتاح Gemini الجديد)"
  echo "4. روح تبويب Actions وشغّل workflowBuild"
fi