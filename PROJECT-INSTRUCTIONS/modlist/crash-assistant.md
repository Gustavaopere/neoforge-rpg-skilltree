# Crash Assistant

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8158b9caf30f953b67e9
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Crash Assistant
- **Arquivo JAR:** `CrashAssistant-neoforge-1.20.6-1.21.4-1.11.12.jar`
- **Versão 1.21.1:** 1.11.12
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Utilitário client-side pós-crash que abre uma GUI para reunir/analisar logs, crash reports e hs_err, facilitar upload/cópia de links e identificar causas conhecidas, com algumas correções assistidas documentadas pelo projeto.
- **Dependências:** Client-only; não é necessário no dedicated server para gameplay. O host top-level contém `/META-INF/jarjar/app.jar` e `/META-INF/jarjar/crash_assistant-neoforge.jar`; o segundo declara `crash_assistant` 1.11.12. Ambos são componentes embedded e não entradas top-level.
- **Sobreposição:** Pode sobrepor launchers/diagnostic tools na coleta e apresentação de logs, mas não substitui o crash report original. Heurísticas e auto-fixes devem ser tratados como assistência, não como prova causal.
- **Compatibilidade/Riscos:** Opera sobre coleta/análise/upload de arquivos de diagnóstico e pode abrir um viewer externo. Riscos: dados sensíveis presentes em logs, comportamento de upload/link viewer, heurísticas de causa falsa-positiva e integração com launchers. 1.11.12 muda `general.upload_to` para `general.wrap_link` e o viewer padrão para [kostromdan.dev](http://kostromdan.dev).
- **Observações:** Host `CrashAssistant-neoforge-1.20.6-1.21.4-1.11.12.jar`, runtime 1.11.12. A faixa `1.20.6-1.21.4` no filename representa versões Minecraft suportadas. O host contém `app.jar` e uma cópia interna `crash_assistant-neoforge.jar` 1.11.12 em `/META-INF/jarjar/`; não criar páginas top-level para essas cópias.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + runtime 1.11.12 + inventário jar-in-jar físico do host + CurseForge oficial Crash Assistant + changelog oficial 1.11.12.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/crash-assistant
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Crash Assistant 1.11.12, post-crash analysis, privacy/viewer, auto-fix boundary e inventário jar-in-jar confirmados no QC global #114. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Crash Assistant 1.11.12 foi reconfirmado fisicamente e reconstruído como ferramenta client-side de diagnóstico; sua instalação não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🩺 Versão física confirmada: `CrashAssistant-neoforge-1.20.6-1.21.4-1.11.12.jar`, runtime `1.11.12`. A faixa `1.20.6-1.21.4` identifica compatibilidade Minecraft do artefato; **não é a versão do mod**.

## 1. Papel e authority
Crash Assistant é uma ferramenta **client-side de diagnóstico pós-crash**. Ele coleta e apresenta evidências produzidas pelo jogo/launcher/JVM; os arquivos originais continuam sendo a fonte primária do incidente.
A análise automática pode apontar causas conhecidas, mas não substitui leitura do stack trace quando a hipótese não é conclusiva.

## 2. Arquivos analisados
O projeto declara suporte a conjuntos afetados de:
- logs do jogo/launcher;
- crash reports;
- arquivos `hs_err` da JVM.

A ferramenta deve preservar o conteúdo original; geração de resumo/link não pode apagar a evidência local necessária a debugging posterior.

## 3. GUI pós-crash
Após um crash compatível, o aplicativo abre uma GUI com os arquivos relevantes, análise e ações auxiliares. Esse fluxo ocorre fora/do lado cliente; não existe state de gameplay server-side controlado pela GUI.

## 4. Upload e links
O projeto oferece ações para enviar logs e copiar links para compartilhamento. Logs podem conter caminhos locais, usernames, modlists, argumentos e outros dados de diagnóstico; o usuário deve tratar upload como divulgação de informação técnica.
A 1.11.12 altera o fluxo de links/viewer e a política associada, portanto integrações/documentação antiga não devem assumir o viewer anterior.

## 5. Changelog 1.11.12 — viewer e config
O changelog oficial registra:
- `general.upload_to` substituído por `general.wrap_link`;
- valor anterior não é migrado e é removido na atualização;
- links copiados passam por padrão a abrir no viewer `kostromdan.dev` em vez de `gnomebot.dev`;
- Privacy Policy atualizada para o novo viewer;
- `general.enable_privacy_policy_acceptance` removido.

Esses são regression gates concretos da build física.

## 6. Análise de causas conhecidas
Crash Assistant reconhece vários padrões conhecidos e oferece ferramentas de análise. Um match heurístico deve ser registrado como **hipótese suportada pelo log**, não como causa provada se o stack trace apontar múltiplos candidates.

## 7. Auto-fixes
O projeto afirma incluir auto-fixes para vários problemas comuns. A ficha não enumera correções que não foram pinadas à 1.11.12.
Qualquer fix automático que modifique config/arquivo deve ser auditável e não ser aplicado em massa ao perfil sem entender a alteração.

## 8. GPU warning
A descrição oficial informa aviso quando Minecraft usa GPU integrada apesar de uma dedicada estar disponível. Esse diagnóstico é client/platform-specific e não implica que toda falha de render seja causada pela GPU selecionada.

## 9. Inventário jar-in-jar do host
O JAR físico hospeda dois componentes em `/META-INF/jarjar/`:
- `app.jar`, componente interno sem identidade top-level própria exposta pela modlist;
- `crash_assistant-neoforge.jar`, que declara mod id `crash_assistant` e versão `1.11.12`.

Pela regra canônica do catálogo, ambos pertencem ao host `CrashAssistant-neoforge-1.20.6-1.21.4-1.11.12.jar` e **não recebem páginas top-level independentes**. Troubleshooting de classpath deve considerar a origem embedded dessas cópias.

## 10. Client/server boundary
O projeto é client-only e declara que, no servidor, não faz nada. Dedicated server não deve depender da GUI/app para iniciar nem para preservar world state.
A presença no cliente não altera a autoridade do crash report/server log original.

## 11. Lifecycle
Validar client boot normal, crash durante startup, crash após world join, coleta dos arquivos corretos, fechamento/reabertura da GUI e atualização de configs entre versões.
A ferramenta não deve criar loop de crash ao tentar analisar um arquivo problemático.

## 12. Privacidade
Antes de upload, verificar o que o log contém e qual serviço/viewer será usado. Mudanças da 1.11.12 tornam documentação de privacy/viewer parte do contract operacional.
Não presumir anonimização de qualquer campo que o upstream não documenta como removido.

## 13. Riscos
1. Upload de informação sensível presente em logs.
2. Heurística indicar causa errada.
3. Auto-fix modificar config indevidamente.
4. Viewer/link behavior divergir de documentação antiga.
5. Config antiga `upload_to` não migrar.
6. Crash Assistant falhar durante o próprio pós-crash.
7. `app.jar` ser catalogado erroneamente como mod separado.
8. Faixa Minecraft do filename ser confundida com versão do mod.

## 14. Matriz de testes
1. Client boot sem crash: nenhuma interferência de gameplay.
2. Crash controlado em perfil de teste → GUI abre.
3. Confirmar seleção de `latest.log`, crash report e `hs_err` quando existentes.
4. Copiar/upload link e verificar viewer/config 1.11.12.
5. Atualização de config antiga contendo `general.upload_to` em cópia de teste.
6. Log com causa conhecida e outro com causa não reconhecida.
7. Cancelar/ignorar auto-fix sem impedir acesso aos logs.
8. Dedicated server sem dependência funcional.
9. Verificar `app.jar` apenas como embedded durante troubleshooting.

## 15. Evidência
- modlist física: Crash Assistant 1.11.12;
- CurseForge oficial: GUI pós-crash, logs/crash reports/hs_err, upload/copy, análise, GPU warning e auto-fixes;
- changelog oficial 1.11.12: `wrap_link`, novo viewer e mudanças de Privacy Policy/config;
- JAR físico: `app.jar` e `crash_assistant-neoforge.jar` 1.11.12 em `/META-INF/jarjar/`, ambos subordinados ao host.

> 🔍 Boundary canônico: Crash Assistant **organiza e interpreta evidência de crash**. O log/crash report original permanece a fonte técnica primária.
